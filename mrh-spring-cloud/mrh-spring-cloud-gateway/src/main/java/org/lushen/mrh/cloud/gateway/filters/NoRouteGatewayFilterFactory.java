package org.lushen.mrh.cloud.gateway.filters;

import org.lushen.mrh.cloud.reference.supports.ServiceBusinessException;
import org.lushen.mrh.cloud.reference.supports.ServiceStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;

/**
 * 无路由 输出提示信息
 * 
 * @author hlm
 */
public class NoRouteGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	public NoRouteGatewayFilterFactory() {
		super(NameConfig.class);
	}

	@Override
	public GatewayFilter apply(NameConfig config) {
		return (exchange, chain) -> {
			throw new ServiceBusinessException(ServiceStatus.HTTP_NOT_FOUND);
		};
	}

}
