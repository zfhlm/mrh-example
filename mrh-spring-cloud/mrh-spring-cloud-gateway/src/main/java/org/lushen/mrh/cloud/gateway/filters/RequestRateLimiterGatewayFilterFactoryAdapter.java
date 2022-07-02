package org.lushen.mrh.cloud.gateway.filters;

import java.util.Map;

import org.lushen.mrh.cloud.reference.supports.StatusCode;
import org.lushen.mrh.cloud.reference.supports.StatusCodeException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;

/**
 * 继承重写 限流过滤器，不返回状态码，而是抛出异常，让异常处理器修饰为提示信息 (看官方后续是否会完善)
 * 
 * @author hlm
 */
public class RequestRateLimiterGatewayFilterFactoryAdapter extends RequestRateLimiterGatewayFilterFactory {

	public RequestRateLimiterGatewayFilterFactoryAdapter(RateLimiter<?> defaultRateLimiter, KeyResolver defaultKeyResolver) {
		super(defaultRateLimiter, defaultKeyResolver);
	}

	@Override
	public String name() {
		return "RequestRateLimiterAdapter";
	}

	private <T> T getOrDefault(T configValue, T defaultValue) {
		return (configValue != null) ? configValue : defaultValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GatewayFilter apply(Config config) {
		
		KeyResolver resolver = getOrDefault(config.getKeyResolver(), getDefaultKeyResolver());
		RateLimiter<Object> limiter = getOrDefault(config.getRateLimiter(), getDefaultRateLimiter());
		boolean denyEmpty = getOrDefault(config.getDenyEmptyKey(), isDenyEmptyKey());

		return (exchange, chain) -> resolver.resolve(exchange).defaultIfEmpty(KEY_RESOLVER_KEY).flatMap(key -> {
			if (KEY_RESOLVER_KEY.equals(key)) {
				if (denyEmpty) {
					throw new StatusCodeException(StatusCode.API_NOT_ACCEPTABLE);
				}
				return chain.filter(exchange);
			}
			String routeId = config.getRouteId();
			if (routeId == null) {
				Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
				routeId = route.getId();
			}
			return limiter.isAllowed(routeId, key).flatMap(response -> {

				for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
					exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
				}

				if (response.isAllowed()) {
					return chain.filter(exchange);
				} else {
					throw new StatusCodeException(StatusCode.USER_FREQUENCY_TOO_QUICKLY);
				}

			});
		});
	}
	
}
