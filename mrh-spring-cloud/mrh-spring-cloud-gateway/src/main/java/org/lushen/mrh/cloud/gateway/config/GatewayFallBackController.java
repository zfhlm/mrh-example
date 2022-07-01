package org.lushen.mrh.cloud.gateway.config;

import org.lushen.mrh.cloud.gateway.supports.GatewayExceptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 网关 熔断限流 fallback
 * 
 * @author hlm
 */
@RestController
public class GatewayFallBackController {

	@Autowired
	private GatewayExceptionConverter exceptionConverter;

	@RequestMapping(path="/fallback", produces="application/json;charset=utf-8")
	@ResponseStatus(HttpStatus.OK)
	public Mono<byte[]> fallback(ServerWebExchange exchange) {
		Throwable cause = exchange.getAttribute(ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR);
		return Mono.just(exceptionConverter.toJsonByteArray(cause));
	}

}
