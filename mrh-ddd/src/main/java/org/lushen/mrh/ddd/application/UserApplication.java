package org.lushen.mrh.ddd.application;

import org.lushen.mrh.ddd.application.user.dto.FindByUserIdReq;
import org.lushen.mrh.ddd.application.user.dto.FindByUserIdRes;
import org.lushen.mrh.ddd.application.user.dto.RegisterUserWithRoleReq;
import org.lushen.mrh.ddd.application.user.dto.RegisterUserWithRoleRes;
import org.lushen.mrh.ddd.application.user.dto.UpdateUserWithRoleReq;
import org.lushen.mrh.ddd.application.user.dto.UpdateUserWithRoleRes;

/**
 * 用户服务
 * 
 * @author hlm
 */
public interface UserApplication {

	/**
	 * 根据 userId 查找用户
	 * 
	 * @param req
	 * @return
	 */
	public FindByUserIdRes findByUserId(FindByUserIdReq req);

	/**
	 * 注册用户
	 * 
	 * @param req
	 */
	public RegisterUserWithRoleRes registerWithRole(RegisterUserWithRoleReq req);

	/**
	 * 更新用户
	 * 
	 * @param req
	 * @return
	 */
	public UpdateUserWithRoleRes updateWithRole(UpdateUserWithRoleReq req);

}
