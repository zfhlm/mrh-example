package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_CONTEXT_GATEWAY_API;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_NAME_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ROLE_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_SOURCE_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_TOKEN_HEADER;

import java.util.Collections;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.gateway.supports.GatewayRoleRepository;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenException;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenException.GatewayTokenExpiredException;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenRepository;
import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverContext;
import org.lushen.mrh.cloud.reference.gateway.GatewayRole;
import org.lushen.mrh.cloud.reference.supports.StatusCode;
import org.lushen.mrh.cloud.reference.supports.StatusCodeException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;
import org.springframework.http.server.reactive.ServerHttpRequest;

import com.google.common.base.Supplier;

/**
 * 用户登录验证、权限验证
 * 
 * @author hlm
 */
public class AuthenticateGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	private final Log log = LogFactory.getLog("AuthenticateFilter");

	private GatewayRoleRepository roleRepository;

	private GatewayTokenGenerator tokenGenerator;

	private GatewayTokenRepository tokenRepository;

	public AuthenticateGatewayFilterFactory(GatewayRoleRepository roleRepository, GatewayTokenGenerator tokenGenerator, GatewayTokenRepository tokenRepository) {
		super(NameConfig.class);
		this.roleRepository = roleRepository;
		this.tokenGenerator = tokenGenerator;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public GatewayFilter apply(NameConfig config) {

		return (exchange, chain) -> {

			// 移除可能被注入的请求头
			ServerHttpRequest request = exchange.getRequest().mutate().headers(headers -> {
				headers.remove(JWT_DELIVER_ID_HEADER);
				headers.remove(JWT_DELIVER_NAME_HEADER);
				headers.remove(JWT_DELIVER_ROLE_ID_HEADER);
				headers.remove(JWT_DELIVER_SOURCE_HEADER);
			}).build();

			// 获取接口信息
			GatewayApi gatewayApi = exchange.getAttribute(EXCHANGE_CONTEXT_GATEWAY_API);
			if(gatewayApi == null) {
				throw new StatusCodeException(StatusCode.API_NOT_FOUND);
			}
			if( ! gatewayApi.isEnabled() ) {
				throw new StatusCodeException(StatusCode.API_NOT_ALLOWED);
			}

			// 匿名接口
			if(gatewayApi.isAnonymous()) {
				return chain.filter(exchange.mutate().request(request).build());
			}

			// 获取登录令牌
			String token = request.getHeaders().getFirst(JWT_TOKEN_HEADER);
			if(token == null) {
				throw new StatusCodeException(StatusCode.USER_NOT_LOGIN);
			}

			// 解析登录令牌
			GatewayDeliverContext context = ((Supplier<GatewayDeliverContext>)() -> {
				try {
					return tokenGenerator.parse(token);
				} catch (GatewayTokenExpiredException e) {
					throw new StatusCodeException(StatusCode.USER_EXPIRED_LOGIN);
				} catch (GatewayTokenException e) {
					log.warn(e.getMessage(), e);
					throw new StatusCodeException(StatusCode.USER_NOT_LOGIN);
				}
			}).get();

			log.info(String.format("HTTP request user %s role %s", context.id(), context.roleId()));

			// 验证登录令牌
			if( ! tokenRepository.validate(token, context) ) {
				throw new StatusCodeException(StatusCode.USER_EXPIRED_LOGIN);
			}

			// 验证角色和权限
			GatewayRole gatewayRole = roleRepository.get(context.roleId());
			if(gatewayRole == null) {
				throw new StatusCodeException(StatusCode.USER_ROLE_NOT_EXIST);
			}
			if( ! gatewayRole.isEnabled() ) {
				throw new StatusCodeException(StatusCode.USER_ROLE_DISABLED);
			}
			if( ! Optional.ofNullable(gatewayRole.getApis()).orElse(Collections.emptySet()).contains(gatewayApi.getId()) ) {
				throw new StatusCodeException(StatusCode.USER_NO_PERMISSION);
			}

			// 添加登录用户信息到请求头
			ServerHttpRequest newRequest = request.mutate().headers(headers -> {
				headers.set(JWT_DELIVER_ID_HEADER, String.valueOf(context.id()));
				headers.set(JWT_DELIVER_NAME_HEADER, context.encodedName());
				headers.set(JWT_DELIVER_ROLE_ID_HEADER, String.valueOf(context.roleId()));
				headers.set(JWT_DELIVER_SOURCE_HEADER, String.valueOf(context.source()));
			}).build();

			return chain.filter(exchange.mutate().request(newRequest).build());

		};

	}

}
