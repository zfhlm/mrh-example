package org.lushen.mrh.cloud.reference.supports.feign.intercept;

import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_NAME_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ROLE_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_SOURCE_HEADER;

import java.util.Optional;
import java.util.function.Function;

import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 传递 {@link GatewayDeliverHeaders} 请求头到远程服务
 * 
 * @author hlm
 */
public class DeliverHeaderRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {

		// 当前请求上下文
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		Function<String, String> function = getFunction(attributes);
		
		// 添加 feign 请求头
		Optional.ofNullable(function.apply(JWT_DELIVER_ID_HEADER)).ifPresent(value -> template.header(JWT_DELIVER_ID_HEADER, value));
		Optional.ofNullable(function.apply(JWT_DELIVER_NAME_HEADER)).ifPresent(value -> template.header(JWT_DELIVER_NAME_HEADER, value));
		Optional.ofNullable(function.apply(JWT_DELIVER_ROLE_ID_HEADER)).ifPresent(value -> template.header(JWT_DELIVER_ROLE_ID_HEADER, value));
		Optional.ofNullable(function.apply(JWT_DELIVER_SOURCE_HEADER)).ifPresent(value -> template.header(JWT_DELIVER_SOURCE_HEADER, value));

	}

	private Function<String, String> getFunction(RequestAttributes attributes) {
		if(attributes instanceof ServletRequestAttributes) {
			return name -> ((ServletRequestAttributes)attributes).getRequest().getHeader(name);
		}
		else if(attributes instanceof WebRequest) {
			return name -> ((WebRequest)attributes).getHeader(name);
		} else {
			return name -> null;
		}
	}

}
