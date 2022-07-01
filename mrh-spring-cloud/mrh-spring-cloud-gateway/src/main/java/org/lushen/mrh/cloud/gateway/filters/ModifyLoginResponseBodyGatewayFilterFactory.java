package org.lushen.mrh.cloud.gateway.filters;

import static org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils.EXCHANGE_CONTEXT_GATEWAY_API;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_TOKEN_HEADER;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.lushen.mrh.cloud.gateway.filters.ModifyLoginResponseBodyGatewayFilterFactory.Config;
import org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils;
import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.lushen.mrh.cloud.reference.supports.StatusCode;
import org.lushen.mrh.cloud.reference.supports.StatusCodeException;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 移动登录 token 到 response json body 过滤器工厂
 * 
 * @author hlm
 */
public class ModifyLoginResponseBodyGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

	public static final int ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;

	private ObjectMapper objectMapper;

	public ModifyLoginResponseBodyGatewayFilterFactory(ObjectMapper objectMapper) {
		super(Config.class);
		this.objectMapper = objectMapper;
	}

	@Override
	public GatewayFilter apply(Config config) {

		return new OrderedGatewayFilter((exchange, chain) -> {

			ServerHttpResponse response = exchange.getResponse();

			return chain.filter(exchange.mutate().response(new ServerHttpResponseDecorator(response) {

				@Override
				public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

					// 非登录接口
					GatewayApi gatewayApi = exchange.getAttribute(EXCHANGE_CONTEXT_GATEWAY_API);
					if(gatewayApi == null || ! gatewayApi.isEnabled() || ! gatewayApi.isLogin() ) {
						return response.writeWith(body);
					}

					// 非 Json body
					if( ! MediaType.APPLICATION_JSON.includes(response.getHeaders().getContentType())) {
						return response.writeWith(body);
					}

					// 响应头 token
					String token = response.getHeaders().getFirst(JWT_TOKEN_HEADER);

					// token 不存在
					if(token == null) {
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

						// 原始 Json body
						byte[] originContent = GatewayExchangeUtils.mergeByteArrays(dataBuffers.stream().map(dataBuffer -> {
							byte[] buffer = new byte[dataBuffer.readableByteCount()];
							dataBuffer.read(buffer);
							DataBufferUtils.release(dataBuffer);
							return buffer;
						}).collect(Collectors.toList()));

						// 更改 Json 添加 token 节点
						byte[] content = null;
						try {
							content = writeTokenToJson(originContent, config.getPropertyPaths(), token);
						} catch (Exception e) {
							throw new StatusCodeException(StatusCode.SERVER_ERROR, e.getMessage(), e);
						}

						// 移除响应头 token
						response.getHeaders().remove(JWT_TOKEN_HEADER);

						return response.bufferFactory().wrap(content);

					}));

				}

			}).build());

		}, ORDER);

	}

	private byte[] writeTokenToJson(byte[] content, String[] propertyPaths, String token) throws Exception {

		// 根节点
		ObjectNode root = (content.length == 0? objectMapper.createObjectNode() : (ObjectNode)objectMapper.readTree(content));

		// 上一节点
		ObjectNode prev = root;

		for(int index=0; index < propertyPaths.length; index ++ ) {

			// 当前节点名称
			String propertyName = propertyPaths[index];

			// 最后一个节点为数据节点，直接替换 token 键值
			if(index == propertyPaths.length - 1) {
				prev.replace(propertyName, prev.textNode(token));
				break;
			}

			// 非最后节点为路径节点
			JsonNode node = prev.get(propertyName);

			// 节点不存在、节点值为 null，创建 Object 节点
			if(node == null || node.isNull()) {
				node = prev.objectNode();
				prev.replace(propertyName, node);
				prev = (ObjectNode)node;
			}
			// 节点为 Object 节点
			else if(node instanceof ObjectNode) {
				prev = (ObjectNode)node;
			}
			// 节点为其他类型节点，抛出异常，不允许添加
			else {
				String path = "/"+Arrays.stream(propertyPaths).limit(index+1).collect(Collectors.joining("/"));
				throw new RuntimeException(String.format("节点类型 %s 路径 %s 存在属性值 %s，无法添加 token !", node.getNodeType(), path, node.asText()));
			}

		}

		// 返回更改后的 Json
		return objectMapper.writeValueAsBytes(root);

	}

	/**
	 * 配置 token 存放的路径，可以指定 N 层
	 * 
	 * @author hlm
	 */
	public static class Config {

		private String[] propertyPaths = {"data", "token"};

		public String[] getPropertyPaths() {
			return propertyPaths;
		}

		public void setPropertyPaths(String[] propertyPaths) {
			this.propertyPaths = propertyPaths;
		}

	}

}
