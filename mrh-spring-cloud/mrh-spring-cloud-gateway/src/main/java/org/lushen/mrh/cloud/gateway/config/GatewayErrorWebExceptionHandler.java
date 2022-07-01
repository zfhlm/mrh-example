package org.lushen.mrh.cloud.gateway.config;

import org.lushen.mrh.cloud.gateway.supports.GatewayExceptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 网关异常处理器
 * 
 * @author hlm
 */
@Component
public class GatewayErrorWebExceptionHandler implements ErrorWebExceptionHandler {

	@Autowired
	private GatewayExceptionConverter exceptionConverter;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable cause) {

		// response header
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

		// response body
		DataBuffer buffer = response.bufferFactory().wrap(exceptionConverter.toJsonByteArray(cause));

		return response.writeWith(Mono.just(buffer));

	}

}
