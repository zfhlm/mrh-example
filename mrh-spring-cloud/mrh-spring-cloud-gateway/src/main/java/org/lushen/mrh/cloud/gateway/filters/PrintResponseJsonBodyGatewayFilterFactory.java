package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED;

import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 日志输出 响应 Json
 * 
 * @author hlm
 */
public class PrintResponseJsonBodyGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> {

	public static final int ORDER = PrintRequestLineGatewayFilterFactory.ORDER + 1;

	private final Log log = LogFactory.getLog("PrintResponseJsonBodyFilter");

	public PrintResponseJsonBodyGatewayFilterFactory() {
		super(NameConfig.class);
	}

	@Override
	public GatewayFilter apply(NameConfig config) {

		return new OrderedGatewayFilter((exchange, chain) -> {

			ServerHttpResponse response = exchange.getResponse();

			return chain.filter(exchange.mutate().response(new ServerHttpResponseDecorator(response) {

				@Override
				public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

					// 默认不输出日志
					if( ! exchange.getAttributeOrDefault(EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED, false) ) {
						return response.writeWith(body);
					}

					// 不输出非 Json 日志
					if( ! MediaType.APPLICATION_JSON.includes(response.getHeaders().getContentType()) ) {
						return response.writeWith(body);
					}

					Flux<? extends DataBuffer> fluxBody = Flux.empty();
					if(body instanceof Flux) {
						fluxBody = ((Flux<? extends DataBuffer>)body);
					}
					else if(body instanceof Mono) {
						fluxBody = ((Mono<? extends DataBuffer>)body).flux();
					}

					return response.writeWith(fluxBody.buffer().map(dataBuffers -> {

						// 读取 body
						byte[] content = GatewayExchangeUtils.mergeByteArrays(dataBuffers.stream().map(dataBuffer -> {
							byte[] buffer = new byte[dataBuffer.readableByteCount()];
							dataBuffer.read(buffer);
							DataBufferUtils.release(dataBuffer);
							return buffer;
						}).collect(Collectors.toList()));

						log.info("HTTP response body : " + new String(content));

						return response.bufferFactory().wrap(content);

					}));

				}

			}).build());

		}, ORDER);

	}

}
