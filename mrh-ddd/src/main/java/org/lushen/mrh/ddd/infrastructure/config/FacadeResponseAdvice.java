package org.lushen.mrh.ddd.infrastructure.config;

import org.lushen.mrh.ddd.infrastructure.basic.IErrorMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 接口响应切面
 * 
 * @author hlm
 */
@ControllerAdvice
public class FacadeResponseAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		if(body == null) {
			return new IErrorMessage(0, "成功!");
		}
		if(body instanceof IErrorMessage) {
			return body;
		}
		if(body instanceof String) {
			return body;
		}

		return new IErrorMessage(0, "成功!", body);
	}

}
