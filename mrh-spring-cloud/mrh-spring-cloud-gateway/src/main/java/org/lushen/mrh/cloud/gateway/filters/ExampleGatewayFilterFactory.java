package org.lushen.mrh.cloud.gateway.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;

/**
 * example 示例
 * 
 * @author hlm
 */
public class ExampleGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	private final Log log = LogFactory.getLog("ExampleFilter");

	public ExampleGatewayFilterFactory() {
		super(NameConfig.class);
	}

	@Override
	public GatewayFilter apply(NameConfig config) {
		return (exchange, chain) -> {
			log.info("example");
			return chain.filter(exchange);
		};
	}

}
