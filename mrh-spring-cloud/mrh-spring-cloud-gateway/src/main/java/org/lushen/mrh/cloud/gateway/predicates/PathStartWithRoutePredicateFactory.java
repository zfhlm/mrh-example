package org.lushen.mrh.cloud.gateway.predicates;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_PREDICATE_MATCHED_PATH_ROUTE_ID_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_PREDICATE_ROUTE_ATTR;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.lushen.mrh.cloud.gateway.predicates.PathStartWithRoutePredicateFactory.Config;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

/**
 * 路径前缀匹配
 * 
 * @author hlm
 */
public class PathStartWithRoutePredicateFactory extends AbstractRoutePredicateFactory<Config> {

	public PathStartWithRoutePredicateFactory() {
		super(Config.class);
	}

	@Override
	public Predicate<ServerWebExchange> apply(Config config) {

		return (exchange -> {

			if(config.getPaths() == null || config.getPaths().isEmpty()) {
				return false;
			}

			String path = exchange.getRequest().getURI().getRawPath();
			for(String prefixPath : config.getPaths()) {
				if(path.startsWith(prefixPath)) {
					String routeId = (String) exchange.getAttributes().get(GATEWAY_PREDICATE_ROUTE_ATTR);
					if (routeId != null) {
						exchange.getAttributes().put(GATEWAY_PREDICATE_MATCHED_PATH_ROUTE_ID_ATTR, routeId);
					}
					return true;
				}
			}

			return false;

		});

	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("paths");
	}

	public static class Config {

		private List<String> paths;

		public List<String> getPaths() {
			return paths;
		}

		public void setPaths(List<String> paths) {
			this.paths = paths;
		}

	}

}
