package org.lushen.mrh.ddd.application.user;

import org.lushen.mrh.ddd.action.role.AssertRoleAction;
import org.lushen.mrh.ddd.action.user.FindUserByIdAction;
import org.lushen.mrh.ddd.action.user.RefreshUserRoleAction;
import org.lushen.mrh.ddd.action.user.SaveUserAction;
import org.lushen.mrh.ddd.action.user.UpdateUserAction;
import org.lushen.mrh.ddd.action.user.dto.FindUserByIdOutDTO;
import org.lushen.mrh.ddd.action.user.dto.RefreshUserRoleInDTO;
import org.lushen.mrh.ddd.action.user.dto.SaveUserInDTO;
import org.lushen.mrh.ddd.action.user.dto.SaveUserOutDTO;
import org.lushen.mrh.ddd.action.user.dto.UpdateUserInDTO;
import org.lushen.mrh.ddd.action.user.dto.UpdateUserOutDTO;
import org.lushen.mrh.ddd.application.UserApplication;
import org.lushen.mrh.ddd.application.user.dto.FindByUserIdReq;
import org.lushen.mrh.ddd.application.user.dto.FindByUserIdRes;
import org.lushen.mrh.ddd.application.user.dto.RegisterUserWithRoleReq;
import org.lushen.mrh.ddd.application.user.dto.RegisterUserWithRoleRes;
import org.lushen.mrh.ddd.application.user.dto.UpdateUserWithRoleReq;
import org.lushen.mrh.ddd.application.user.dto.UpdateUserWithRoleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserApplicationImpl implements UserApplication {

	@Autowired
	private AssertRoleAction assertRoleAction;
	@Autowired
	private FindUserByIdAction findUserByIdAction;
	@Autowired
	private SaveUserAction saveUserAction;
	@Autowired
	private UpdateUserAction updateUserAction;
	@Autowired
	private RefreshUserRoleAction refreshUserRoleAction;

	@Override
	public FindByUserIdRes findByUserId(FindByUserIdReq req) {

		// 获取用户
		FindUserByIdOutDTO out = findUserByIdAction.execute(req.getUserId());

		// 返回用户
		if(out == null) {
			return null;
		} else {
			FindByUserIdRes res = new FindByUserIdRes();
			res.setUserId(out.getUserId());
			res.setName(out.getName());
			res.setCreateTime(out.getCreateTime());
			res.setCreateUser(out.getCreateUser());
			res.setUpdateTime(out.getUpdateTime());
			res.setUpdateUser(out.getUpdateUser());
			return res;
		}
	}

	@Transactional
	@Override
	public RegisterUserWithRoleRes registerWithRole(RegisterUserWithRoleReq req) {

		// 验证角色
		req.getRoleIds().forEach(roleId -> assertRoleAction.execute(roleId));

		// 注册用户
		SaveUserInDTO saveUserIn = new SaveUserInDTO();
		saveUserIn.setName(req.getName());
		SaveUserOutDTO saveUserOut = saveUserAction.execute(saveUserIn);

		// 刷新用户角色
		RefreshUserRoleInDTO refreshUserRoleIn = new RefreshUserRoleInDTO();
		refreshUserRoleIn.setUserId(saveUserOut.getUserId());
		refreshUserRoleIn.setRoleIds(req.getRoleIds());
		refreshUserRoleAction.execute(refreshUserRoleIn);

		// 返回用户
		RegisterUserWithRoleRes res = new RegisterUserWithRoleRes();
		res.setUserId(saveUserOut.getUserId());
		res.setName(saveUserOut.getName());
		res.setCreateTime(saveUserOut.getCreateTime());
		res.setCreateUser(saveUserOut.getCreateUser());
		res.setUpdateTime(saveUserOut.getUpdateTime());
		res.setUpdateUser(saveUserOut.getUpdateUser());

		return res;
	}

	@Transactional
	@Override
	public UpdateUserWithRoleRes updateWithRole(UpdateUserWithRoleReq req) {

		// 验证角色
		req.getRoleIds().forEach(roleId -> assertRoleAction.execute(roleId));

		// 更新用户
		UpdateUserInDTO updateUserIn = new UpdateUserInDTO();
		updateUserIn.setUserId(req.getUserId());
		updateUserIn.setName(req.getName());
		UpdateUserOutDTO updateUserOut = updateUserAction.execute(updateUserIn);

		// 刷新用户角色
		RefreshUserRoleInDTO refreshUserRoleIn = new RefreshUserRoleInDTO();
		refreshUserRoleIn.setUserId(req.getUserId());
		refreshUserRoleIn.setRoleIds(req.getRoleIds());
		refreshUserRoleAction.execute(refreshUserRoleIn);

		// 返回用户
		UpdateUserWithRoleRes res = new UpdateUserWithRoleRes();
		res.setUserId(updateUserOut.getUserId());
		res.setName(updateUserOut.getName());
		res.setCreateTime(updateUserOut.getCreateTime());
		res.setCreateUser(updateUserOut.getCreateUser());
		res.setUpdateTime(updateUserOut.getUpdateTime());
		res.setUpdateUser(updateUserOut.getUpdateUser());

		return res;
	}

}
