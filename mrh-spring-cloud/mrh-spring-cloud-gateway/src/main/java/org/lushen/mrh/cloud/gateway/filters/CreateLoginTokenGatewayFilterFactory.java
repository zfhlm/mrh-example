package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_CONTEXT_GATEWAY_API;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_NAME_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ROLE_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_SOURCE_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_TOKEN_HEADER;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenException;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenRepository;
import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverContext;
import org.lushen.mrh.cloud.reference.supports.StatusCode;
import org.lushen.mrh.cloud.reference.supports.StatusCodeException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;
import org.springframework.http.HttpHeaders;

import reactor.core.publisher.Mono;

/**
 * 更改登录接口响应头，生成登录 token 过滤器工厂
 * 
 * @author hlm
 */
public class CreateLoginTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	private final Log log = LogFactory.getLog("CreateLoginTokenFilter");

	private GatewayTokenGenerator tokenGenerator;

	private GatewayTokenRepository tokenRepository;

	public CreateLoginTokenGatewayFilterFactory(GatewayTokenGenerator tokenGenerator, GatewayTokenRepository tokenRepository) {
		super(NameConfig.class);
		this.tokenGenerator = tokenGenerator;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public GatewayFilter apply(NameConfig config) {

		return (exchange, chain) -> {

			// 获取接口信息
			GatewayApi gatewayApi = exchange.getAttribute(EXCHANGE_CONTEXT_GATEWAY_API);
			if(gatewayApi == null) {
				throw new StatusCodeException(StatusCode.API_NOT_FOUND);
			}
			if( ! gatewayApi.isEnabled() ) {
				throw new StatusCodeException(StatusCode.API_NOT_ALLOWED);
			}

			// 非登录接口
			if( ! gatewayApi.isLogin() ) {
				return chain.filter(exchange).then(Mono.fromRunnable(() -> exchange.getResponse().getHeaders().remove(JWT_TOKEN_HEADER)));
			}

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {

				HttpHeaders headers = exchange.getResponse().getHeaders();

				// 登录接口返回的登录信息
				GatewayDeliverContext context = GatewayDeliverContext.create(key -> headers.getFirst(key));

				log.info(String.format("HTTP login user %s role %s", context.id(), context.roleId()));

				// 生成登录令牌
				String token = null;
				try {
					token = tokenGenerator.create(context);
				} catch (GatewayTokenException e) {
					throw new StatusCodeException(StatusCode.SERVER_ERROR);
				}

				// 存储登录令牌
				tokenRepository.update(token, context);

				// 添加登录令牌到响应头，并移除登录用户响应头信息
				headers.set(JWT_TOKEN_HEADER, token);
				headers.remove(JWT_DELIVER_ID_HEADER);
				headers.remove(JWT_DELIVER_NAME_HEADER);
				headers.remove(JWT_DELIVER_ROLE_ID_HEADER);
				headers.remove(JWT_DELIVER_SOURCE_HEADER);

			}));

		};

	}

}
