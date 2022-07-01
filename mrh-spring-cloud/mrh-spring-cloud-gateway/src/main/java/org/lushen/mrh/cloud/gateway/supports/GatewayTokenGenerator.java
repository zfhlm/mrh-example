package org.lushen.mrh.cloud.gateway.supports;

import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverContext;

/**
 * 网关令牌生成器
 * 
 * @author hlm
 */
public interface GatewayTokenGenerator {

	/**
	 * 生成令牌
	 * 
	 * @param context
	 * @return
	 * @throws GatewayTokenException
	 */
	public String create(GatewayDeliverContext context) throws GatewayTokenException;

	/**
	 * 解析令牌
	 * 
	 * @param token
	 * @return
	 * @throws GatewayTokenException
	 */
	public GatewayDeliverContext parse(String token) throws GatewayTokenException;

}
