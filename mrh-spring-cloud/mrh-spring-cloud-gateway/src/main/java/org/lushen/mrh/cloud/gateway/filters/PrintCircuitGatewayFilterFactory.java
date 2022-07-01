package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED;
import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_REQUEST_LINE_ENABLED;
import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED;
import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.PRINT_CIRCUIT_FILTER_ORDER;

import org.lushen.mrh.cloud.gateway.filters.PrintCircuitGatewayFilterFactory.Config;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * 日志输出 开关
 * 
 * @author hlm
 */
public class PrintCircuitGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

	public PrintCircuitGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return new OrderedGatewayFilter((exchange, chain) -> {
			exchange.getAttributes().put(EXCHANGE_PRINT_REQUEST_LINE_ENABLED, config.isPrintRequestLine());
			exchange.getAttributes().put(EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED, config.isPrintRequestJson());
			exchange.getAttributes().put(EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED, config.isPrintResponseJson());
			return chain.filter(exchange);
		}, PRINT_CIRCUIT_FILTER_ORDER);
	}

	public static class Config {

		private boolean printRequestLine = true;

		private boolean printRequestJson = true;

		private boolean printResponseJson = true;

		public void setPrintRequestLine(boolean printRequestLine) {
			this.printRequestLine = printRequestLine;
		}

		public void setPrintRequestJson(boolean printRequestJson) {
			this.printRequestJson = printRequestJson;
		}

		public void setPrintResponseJson(boolean printResponseJson) {
			this.printResponseJson = printResponseJson;
		}

		public boolean isPrintRequestLine() {
			return printRequestLine;
		}

		public boolean isPrintRequestJson() {
			return printRequestJson;
		}

		public boolean isPrintResponseJson() {
			return printResponseJson;
		}

	}

}
