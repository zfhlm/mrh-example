package org.lushen.mrh.ddd.application;

import org.lushen.mrh.ddd.application.role.dto.FindByRoleIdReq;
import org.lushen.mrh.ddd.application.role.dto.FindByRoleIdRes;
import org.lushen.mrh.ddd.application.role.dto.RegisterRoleReq;
import org.lushen.mrh.ddd.application.role.dto.RegisterRoleRes;

/**
 * 角色服务
 * 
 * @author hlm
 */
public interface RoleApplication {

	/**
	 * 根据 roleId 查询角色
	 * 
	 * @param req
	 * @return
	 */
	public FindByRoleIdRes findByRoleId(FindByRoleIdReq req);

	/**
	 * 注册角色
	 * 
	 * @param req
	 * @return
	 */
	public RegisterRoleRes registerRole(RegisterRoleReq req);

}
