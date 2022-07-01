package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED;
import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.PRINT_REQUEST_JSON_BODY_FILTER_ORDER;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 日志输出 请求 Json
 * 
 * @author hlm
 */
public class PrintRequestJsonBodyGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	private final Log log = LogFactory.getLog("PrintRequestJsonBodyFilter");

	private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

	public PrintRequestJsonBodyGatewayFilterFactory() {
		super(NameConfig.class);
	}

	@Override
	public GatewayFilter apply(NameConfig config) {

		return new OrderedGatewayFilter((exchange, chain) -> {

			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			// 默认不输出日志
			if( ! exchange.getAttributeOrDefault(EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED, false) ) {
				return chain.filter(exchange);
			}

			// 不输出非 Json 日志
			if( ! MediaType.APPLICATION_JSON.includes(request.getHeaders().getContentType()) ) {
				return chain.filter(exchange);
			}

			// 读取并输出 Json 日志
			ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
			Mono<byte[]> modifiedBody = serverRequest.bodyToMono(byte[].class).flatMap(body -> {
				log.info("HTTP request body : " + new String(body));
				return Mono.just(body);
			});

			// 重新添加到请求 body
			HttpHeaders headers = new HttpHeaders();
			headers.putAll(request.getHeaders());
			CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
			return BodyInserters.fromPublisher(modifiedBody, byte[].class)
					.insert(outputMessage, new BodyInserterContext())
					.then(Mono.defer(() -> {
						ServerHttpRequestDecorator newRequest = new ServerHttpRequestDecorator(request) {
							@Override
							public Flux<DataBuffer> getBody() {
								return outputMessage.getBody();
							}
						};
						return chain.filter(exchange.mutate().request(newRequest).response(response).build());
					}));

		}, PRINT_REQUEST_JSON_BODY_FILTER_ORDER);

	}

}
