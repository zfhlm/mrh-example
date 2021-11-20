package org.lushen.mrh.ddd.application.role;

import org.lushen.mrh.ddd.action.role.FindRoleByIdAction;
import org.lushen.mrh.ddd.action.role.SaveRoleAction;
import org.lushen.mrh.ddd.action.role.dto.FindRoleByIdOutDTO;
import org.lushen.mrh.ddd.action.role.dto.SaveRoleInDTO;
import org.lushen.mrh.ddd.action.role.dto.SaveRoleOutDTO;
import org.lushen.mrh.ddd.application.RoleApplication;
import org.lushen.mrh.ddd.application.role.dto.FindByRoleIdReq;
import org.lushen.mrh.ddd.application.role.dto.FindByRoleIdRes;
import org.lushen.mrh.ddd.application.role.dto.RegisterRoleReq;
import org.lushen.mrh.ddd.application.role.dto.RegisterRoleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleApplicationImpl implements RoleApplication {

	@Autowired
	private SaveRoleAction saveRoleAction;
	@Autowired
	private FindRoleByIdAction findRoleByIdAction;

	@Override
	public FindByRoleIdRes findByRoleId(FindByRoleIdReq req) {

		// 获取角色
		FindRoleByIdOutDTO out = findRoleByIdAction.execute(req.getRoleId());

		// 返回角色
		if(out == null) {
			return null;
		} else {
			FindByRoleIdRes res = new FindByRoleIdRes();
			res.setRoleId(out.getRoleId());
			res.setRoleName(out.getRoleName());
			res.setRemarks(out.getRemarks());
			res.setIsEnabled(out.getIsEnabled());
			res.setCreateTime(out.getCreateTime());
			res.setCreateUser(out.getCreateUser());
			res.setUpdateTime(out.getUpdateTime());
			res.setUpdateUser(out.getUpdateUser());
			return res;
		}
	}

	@Transactional
	@Override
	public RegisterRoleRes registerRole(RegisterRoleReq req) {

		// 注册角色
		SaveRoleInDTO in = new SaveRoleInDTO();
		in.setRoleName(req.getRoleName());
		in.setRemarks(in.getRemarks());
		in.setIsEnabled(req.getIsEnabled());
		SaveRoleOutDTO out = saveRoleAction.execute(in);

		// 返回角色
		RegisterRoleRes res = new RegisterRoleRes();
		res.setRoleId(out.getRoleId());
		res.setRoleName(out.getRoleName());
		res.setRemarks(out.getRemarks());
		res.setIsEnabled(out.getIsEnabled());
		res.setCreateTime(out.getCreateTime());
		res.setCreateUser(out.getCreateUser());
		res.setUpdateTime(out.getUpdateTime());
		res.setUpdateUser(out.getUpdateUser());

		return res;
	}

}
