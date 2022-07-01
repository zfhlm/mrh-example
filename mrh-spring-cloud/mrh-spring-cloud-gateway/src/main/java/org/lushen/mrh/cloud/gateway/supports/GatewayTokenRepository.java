package org.lushen.mrh.cloud.gateway.supports;

import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverContext;

/**
 * 网关令牌存储接口，可扩展，例如使用 redis 废弃用户令牌
 * 
 * @author hlm
 */
public interface GatewayTokenRepository {

	/**
	 * 更新用户令牌信息
	 * 
	 * @param token
	 * @param context
	 */
	public void update(String token, GatewayDeliverContext context);

	/**
	 * 验证用户令牌信息
	 * 
	 * @param token
	 * @param context
	 * @return
	 */
	public boolean validate(String token, GatewayDeliverContext context);

}
