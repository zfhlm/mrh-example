package org.lushen.mrh.cloud.gateway.supports;

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
	public String create(GatewayTokenContext context) throws GatewayTokenException;

	/**
	 * 解析令牌
	 * 
	 * @param token
	 * @return
	 * @throws GatewayTokenException
	 */
	public GatewayTokenContext parse(String token) throws GatewayTokenException;

}
