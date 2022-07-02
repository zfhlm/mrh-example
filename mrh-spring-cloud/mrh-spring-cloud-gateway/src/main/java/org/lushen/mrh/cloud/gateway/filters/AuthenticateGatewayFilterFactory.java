package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_EXPIRED_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_NAME_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ROLE_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_SOURCE_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_TOKEN_HEADER;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.gateway.supports.GatewayApiMacther;
import org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils;
import org.lushen.mrh.cloud.gateway.supports.GatewayRoleMatcher;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator.GatewayTokenException;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator.GatewayTokenExpiredException;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenObject;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenRepository;
import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.lushen.mrh.cloud.reference.gateway.GatewayRole;
import org.lushen.mrh.cloud.reference.supports.StatusCode;
import org.lushen.mrh.cloud.reference.supports.StatusCodeException;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;
import org.springframework.http.HttpHeaders;

import com.google.common.base.Supplier;

import reactor.core.publisher.Mono;

/**
 * 用户登录验证、权限验证、生成登录令牌
 * 
 * @author hlm
 */
public class AuthenticateGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	private final Log log = LogFactory.getLog("AuthenticateFilter");

	private final ApplicationConversionService conversionService = new ApplicationConversionService();

	private GatewayApiMacther apiMacther;

	private GatewayRoleMatcher roleMatcher;

	private GatewayTokenGenerator tokenGenerator;

	private GatewayTokenRepository tokenRepository;

	public AuthenticateGatewayFilterFactory(
			GatewayApiMacther apiMacther,
			GatewayRoleMatcher roleMatcher,
			GatewayTokenGenerator tokenGenerator, 
			GatewayTokenRepository tokenRepository) {
		super(NameConfig.class);
		this.apiMacther = apiMacther;
		this.roleMatcher = roleMatcher;
		this.tokenGenerator = tokenGenerator;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public GatewayFilter apply(NameConfig config) {

		return (exchange, chain) -> {

			// 获取接口信息
			GatewayApi gatewayApi = apiMacther.match(exchange.getRequest().getMethod(), exchange.getRequest().getPath().value());
			if(gatewayApi == null) {
				throw new StatusCodeException(StatusCode.API_NOT_FOUND);
			}
			if( ! gatewayApi.isEnabled() ) {
				throw new StatusCodeException(StatusCode.API_NOT_ALLOWED);
			}

			return Mono.just(exchange.getRequest().mutate().headers(headers -> {

				// 移除可能被注入的请求头
				headers.remove(JWT_DELIVER_ID_HEADER);
				headers.remove(JWT_DELIVER_NAME_HEADER);
				headers.remove(JWT_DELIVER_ROLE_ID_HEADER);
				headers.remove(JWT_DELIVER_SOURCE_HEADER);

			}).build()).flatMap(request -> {

				// 允许匿名访问
				if(gatewayApi.isAnonymous()) {
					return Mono.just(request);
				}

				// 获取登录令牌
				String token = request.getHeaders().getFirst(JWT_TOKEN_HEADER);
				if(token == null) {
					throw new StatusCodeException(StatusCode.USER_NOT_LOGIN);
				}

				// 解析登录令牌
				GatewayTokenObject tokenObject = ((Supplier<GatewayTokenObject>)() -> {
					try {
						return tokenGenerator.parse(token);
					} catch (GatewayTokenExpiredException e) {
						throw new StatusCodeException(StatusCode.USER_EXPIRED_LOGIN);
					} catch (GatewayTokenException e) {
						log.warn(e.getMessage(), e);
						throw new StatusCodeException(StatusCode.USER_NOT_LOGIN);
					}
				}).get();

				log.info(String.format("HTTP request user %s role %s", tokenObject.getId(), tokenObject.getRoleId()));

				// 验证角色和权限
				GatewayRole gatewayRole = roleMatcher.match(tokenObject.getRoleId());
				if(gatewayRole == null) {
					throw new StatusCodeException(StatusCode.USER_ROLE_NOT_EXIST);
				}
				if( ! gatewayRole.isEnabled() ) {
					throw new StatusCodeException(StatusCode.USER_ROLE_DISABLED);
				}
				if( ! Optional.ofNullable(gatewayRole.getApis()).orElse(Collections.emptySet()).contains(gatewayApi.getId()) ) {
					throw new StatusCodeException(StatusCode.USER_NO_PERMISSION);
				}

				// 验证登录令牌
				return tokenRepository.validate(token, tokenObject).flatMap(correct -> {
					if( ! correct ) {
						throw new StatusCodeException(StatusCode.USER_EXPIRED_LOGIN);
					}
					// 添加登录用户信息到请求头
					return Mono.just(request.mutate().headers(headers -> {
						headers.set(JWT_DELIVER_ID_HEADER, String.valueOf(tokenObject.getId()));
						headers.set(JWT_DELIVER_NAME_HEADER, GatewayExchangeUtils.urlEncode(tokenObject.getName()));
						headers.set(JWT_DELIVER_ROLE_ID_HEADER, String.valueOf(tokenObject.getRoleId()));
						headers.set(JWT_DELIVER_SOURCE_HEADER, String.valueOf(tokenObject.getSource()));
					}).build());
				});

			}).flatMap(request -> {

				return chain.filter(exchange.mutate().request(request).build()).then(Mono.defer(() -> {

					HttpHeaders headers = exchange.getResponse().getHeaders();
					headers.remove(JWT_TOKEN_HEADER);

					// 非登录接口
					if( ! gatewayApi.isLogin() ) {
						return Mono.<Void>empty();
					}

					// 登录接口返回的登录信息
					GatewayTokenObject tokenObject = new GatewayTokenObject();
					tokenObject.setId(Long.parseLong(headers.getFirst(JWT_DELIVER_ID_HEADER)));
					tokenObject.setName(GatewayExchangeUtils.urlDecode(headers.getFirst(JWT_DELIVER_NAME_HEADER)));
					tokenObject.setRoleId(Long.parseLong(headers.getFirst(JWT_DELIVER_ROLE_ID_HEADER)));
					tokenObject.setSource(Integer.parseInt(headers.getFirst(JWT_DELIVER_SOURCE_HEADER)));
					tokenObject.setExpired(conversionService.convert(headers.getFirst(JWT_DELIVER_EXPIRED_HEADER), Duration.class));

					// 验证登录信息
					if(tokenObject.getExpired() == null) {
						log.error("HTTP login expired header can not be null !");
						return Mono.<Void>error(new StatusCodeException(StatusCode.SERVER_ERROR));
					}

					log.info(String.format("HTTP login user %s role %s", tokenObject.getId(), tokenObject.getRoleId()));

					// 生成登录令牌
					String token = null;
					try {
						token = tokenGenerator.create(tokenObject);
					} catch (GatewayTokenException e) {
						log.error(e.getMessage(), e);
						return Mono.<Void>error(new StatusCodeException(StatusCode.SERVER_ERROR));
					}

					// 添加登录令牌到响应头，并移除登录用户响应头信息
					headers.set(JWT_TOKEN_HEADER, token);
					headers.remove(JWT_DELIVER_ID_HEADER);
					headers.remove(JWT_DELIVER_NAME_HEADER);
					headers.remove(JWT_DELIVER_ROLE_ID_HEADER);
					headers.remove(JWT_DELIVER_SOURCE_HEADER);

					// 存储登录令牌
					return tokenRepository.update(token, tokenObject).flatMap(b -> Mono.<Void>empty()).doOnError(error -> {
						headers.remove(JWT_TOKEN_HEADER);
						Mono.<Void>error(error);
					});

				}));

			});

		};

	}

}
