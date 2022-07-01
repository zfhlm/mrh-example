package org.lushen.mrh.cloud.gateway.supports;

import org.lushen.mrh.cloud.reference.gateway.GatewayRole;

/**
 * 网关 role 查询接口
 * 
 * @author hlm
 */
public interface GatewayRoleRepository {

	/**
	 * 获取 role 信息
	 * 
	 * @param id
	 * @return
	 */
	public GatewayRole get(long id);

}
