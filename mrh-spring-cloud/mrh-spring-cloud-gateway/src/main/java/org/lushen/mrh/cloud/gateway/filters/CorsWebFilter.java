package org.lushen.mrh.cloud.gateway.filters;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

/**
 * 允许跨域过滤器
 * 
 * @author hlm
 */
public class CorsWebFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		if (CorsUtils.isCorsRequest(request)) {
			HttpHeaders headers = exchange.getResponse().getHeaders();
			headers.set(ACCESS_CONTROL_ALLOW_ORIGIN, Optional.ofNullable(request.getHeaders().getOrigin()).orElse("*"));
			headers.set(ACCESS_CONTROL_ALLOW_HEADERS, "*");
			headers.set(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS");
			headers.set(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
			headers.set(ACCESS_CONTROL_EXPOSE_HEADERS, "*");
			headers.set(ACCESS_CONTROL_MAX_AGE, "7200");
		}
		if (request.getMethod() == HttpMethod.OPTIONS) {
			return Mono.empty();
		} else {
			return chain.filter(exchange);
		}
	}

}
