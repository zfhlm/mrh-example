package org.lushen.mrh.ddd.action.role;

import org.lushen.mrh.ddd.action.role.dto.FindRoleByIdOutDTO;
import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TRoleMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 根据 roleId 查询角色
 * 
 * @author hlm
 */
@Component
public class FindRoleByIdAction implements IAction<Integer, FindRoleByIdOutDTO> {

	@Autowired
	private TRoleMapper roleMapper;

	@Override
	public FindRoleByIdOutDTO execute(Integer roleId) {

		// 查询角色
		TRole role = roleMapper.selectByPrimaryKey(roleId);

		if(role == null) {
			return null;
		}

		// 返回数据
		FindRoleByIdOutDTO out = new FindRoleByIdOutDTO();
		out.setRoleId(role.getRoleId());
		out.setRoleName(role.getRoleName());
		out.setRemarks(role.getRemarks());
		out.setIsEnabled(role.getIsEnabled());
		out.setCreateTime(role.getCreateTime());
		out.setCreateUser(role.getCreateUser());
		out.setUpdateTime(role.getUpdateTime());
		out.setUpdateUser(role.getUpdateUser());

		return out;
	}

}
