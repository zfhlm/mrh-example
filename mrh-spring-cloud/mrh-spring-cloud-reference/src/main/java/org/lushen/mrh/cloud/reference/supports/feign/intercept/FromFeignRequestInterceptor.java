package org.lushen.mrh.cloud.reference.supports.feign.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 添加请求头，标识当前请求为 feign 调用请求
 * 
 * @author hlm
 */
public class FromFeignRequestInterceptor implements RequestInterceptor {

	public static final String REQUEST_FROM_FEIGN_HEADER = "Request-From-Feign";

	public static final String REQUEST_FROM_FEIGN_VALUE = "true";

	@Override
	public void apply(RequestTemplate template) {
		template.header(REQUEST_FROM_FEIGN_HEADER, REQUEST_FROM_FEIGN_VALUE);
	}

}
