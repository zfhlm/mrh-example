package org.lushen.mrh.cloud.gateway.supports;

import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.springframework.http.HttpMethod;

/**
 * 网关 api 信息查询接口
 * 
 * @author hlm
 */
public interface GatewayApiRepository {

	/**
	 * 获取 api 信息
	 * 
	 * @param method
	 * @param path
	 * @return
	 */
	public GatewayApi get(HttpMethod method, String path);

}
