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
import org.springframework.http.server.reactive.ServerHttpResponse;
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
		ServerHttpResponse response = exchange.getResponse();
		if (CorsUtils.isCorsRequest(request)) {
			HttpHeaders headers = response.getHeaders();
			if( ! headers.containsKey(ACCESS_CONTROL_ALLOW_ORIGIN) ) {
				headers.set(ACCESS_CONTROL_ALLOW_ORIGIN, Optional.ofNullable(request.getHeaders().getOrigin()).orElse("*"));
			}
			if( ! headers.containsKey(ACCESS_CONTROL_ALLOW_HEADERS) ) {
				headers.set(ACCESS_CONTROL_ALLOW_HEADERS, "*");
			}
			if( ! headers.containsKey(ACCESS_CONTROL_ALLOW_METHODS) ) {
				headers.set(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS");
			}
			if( ! headers.containsKey(ACCESS_CONTROL_ALLOW_CREDENTIALS) ) {
				headers.set(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
			}
			if( ! headers.containsKey(ACCESS_CONTROL_EXPOSE_HEADERS) ) {
				headers.set(ACCESS_CONTROL_EXPOSE_HEADERS, "*");
			}
			if( ! headers.containsKey(ACCESS_CONTROL_MAX_AGE) ) {
				headers.set(ACCESS_CONTROL_MAX_AGE, "7200");
			}
		}
		if (request.getMethod() == HttpMethod.OPTIONS) {
			return Mono.empty();
		} else {
			return chain.filter(exchange);
		}
	}

}
