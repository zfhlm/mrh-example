package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_CONTEXT_GATEWAY_API;
import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED;
import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_REQUEST_LINE_ENABLED;
import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED;

import org.lushen.mrh.cloud.gateway.supports.GatewayApiRepository;
import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 发布 {@link GatewayApi} 到服务请求响应上下文 过滤器工厂
 * 
 * @author hlm
 */
public class DeployApiGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	public static final int ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1000;

	private GatewayApiRepository apiRepository;

	public DeployApiGatewayFilterFactory(GatewayApiRepository apiRepository) {
		super(NameConfig.class);
		this.apiRepository = apiRepository;
	}

	@Override
	public GatewayFilter apply(NameConfig config) {

		return new OrderedGatewayFilter((exchange, chain) -> {

			ServerHttpRequest request = exchange.getRequest();

			// 查找接口
			GatewayApi gatewayApi = apiRepository.get(request.getMethod(), request.getPath().value());

			// 添加上下文信息
			if(gatewayApi != null) {
				exchange.getAttributes().put(EXCHANGE_CONTEXT_GATEWAY_API, gatewayApi);
				exchange.getAttributes().put(EXCHANGE_PRINT_REQUEST_LINE_ENABLED, gatewayApi.isPrintReqLine());
				exchange.getAttributes().put(EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED, gatewayApi.isPrintReqJson());
				exchange.getAttributes().put(EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED, gatewayApi.isPrintResJson());
			}

			return chain.filter(exchange);

		}, ORDER);

	}

}
