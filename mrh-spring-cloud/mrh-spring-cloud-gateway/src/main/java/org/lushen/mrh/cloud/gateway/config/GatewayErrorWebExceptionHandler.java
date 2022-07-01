package org.lushen.mrh.cloud.gateway.config;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private final Log log = LogFactory.getLog(GatewayErrorWebExceptionHandler.class.getSimpleName());

	@Autowired
	private GatewayExceptionConverter exceptionConverter;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable cause) {

		// response header
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");

		// response body
		byte[] body = exceptionConverter.toJsonByteArray(cause);
		DataBuffer buffer = response.bufferFactory().wrap(body);

		// 输出日志
		if(exchange.getAttributeOrDefault(EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED, false)) {
			log.info("HTTP response body : " + new String(body));
		}

		return response.writeWith(Mono.just(buffer));

	}

}
