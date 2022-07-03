package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.Exchanges.EXCHANGE_PRINT_REQUEST_LINE_ENABLED;
import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.Orders.PRINT_REQUEST_LINE_FILTER_ORDER;

import org.lushen.mrh.cloud.gateway.supports.GatewayLogger;
import org.lushen.mrh.cloud.gateway.supports.GatewayLoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 日志输出 请求 line
 * 
 * @author hlm
 */
public class PrintRequestLineGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	private final GatewayLogger log = GatewayLoggerFactory.getLog("PrintRequestLineFilter");

	public PrintRequestLineGatewayFilterFactory() {
		super(NameConfig.class);
	}

	@Override
	public GatewayFilter apply(NameConfig config) {

		return new OrderedGatewayFilter((exchange, chain) -> {

			// 默认不输出日志
			if( ! exchange.getAttributeOrDefault(EXCHANGE_PRINT_REQUEST_LINE_ENABLED, false) ) {
				return chain.filter(exchange);
			}

			ServerHttpRequest originRequest = exchange.getRequest();
			String method = originRequest.getMethodValue();
			String path = originRequest.getPath().value();
			String query = originRequest.getURI().getRawQuery();

			// 输出日志
			if(query != null) {
				log.info(exchange, String.format("HTTP %s %s - %s", method, path, query));
			} else {
				log.info(exchange, String.format("HTTP %s %s", method, path));
			}

			return chain.filter(exchange);

		}, PRINT_REQUEST_LINE_FILTER_ORDER);

	}

}
