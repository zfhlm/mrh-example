package org.lushen.mrh.cloud.gateway.supports;

import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.springframework.http.HttpMethod;

/**
 * 网关 api 匹配接口
 * 
 * @author hlm
 */
public interface GatewayApiMacther {

	/**
	 * 获取 api 信息
	 * 
	 * @param method
	 * @param path
	 * @return
	 */
	public GatewayApi match(HttpMethod method, String path);

}
