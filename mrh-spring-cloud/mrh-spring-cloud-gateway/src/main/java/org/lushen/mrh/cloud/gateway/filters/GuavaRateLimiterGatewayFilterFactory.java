package org.lushen.mrh.cloud.gateway.filters;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.lushen.mrh.cloud.gateway.filters.GuavaRateLimiterGatewayFilterFactory.Config;
import org.lushen.mrh.cloud.reference.supports.ServiceBusinessException;
import org.lushen.mrh.cloud.reference.supports.ServiceStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 基于 guava 令牌桶进行限流
 * 
 * @author hlm
 */
public class GuavaRateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

	private static final int defaultPermitsPerSecond = 50;

	private final ConcurrentMap<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

	public GuavaRateLimiterGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			// 获取当前路由限流器
			Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
			RateLimiter rateLimiter = rateLimiters.computeIfAbsent(route.getId(), k -> {
				if(config.getPermitsPerSecond() != null && config.getPermitsPerSecond() > 0) {
					return RateLimiter.create(config.getPermitsPerSecond());
				} else {
					return RateLimiter.create(defaultPermitsPerSecond);
				}
			});
			// 根据获取令牌结果，决定执行或抛出异常
			if(rateLimiter.tryAcquire()) {
				return chain.filter(exchange);
			} else {
				throw new ServiceBusinessException(ServiceStatus.EXTEND_SERVER_BUSY_ERRROR);
			}
		};
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("permitsPerSecond");
	}

	public static class Config {

		// 每秒产生多少个令牌
		private Integer permitsPerSecond;

		public Integer getPermitsPerSecond() {
			return permitsPerSecond;
		}

		public void setPermitsPerSecond(Integer permitsPerSecond) {
			this.permitsPerSecond = permitsPerSecond;
		}

	}

}
