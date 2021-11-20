package org.lushen.mrh.ddd.action.role;

import java.util.Date;

import org.lushen.mrh.ddd.action.role.dto.UpdateRoleInDTO;
import org.lushen.mrh.ddd.action.role.dto.UpdateRoleOutDTO;
import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.basic.IBusinessException;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TRoleMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新角色 action
 * 
 * @author hlm
 */
@Component
public class UpdateRoleAction implements IAction<UpdateRoleInDTO, UpdateRoleOutDTO> {

	@Autowired
	private TRoleMapper roleMapper;

	@Override
	public UpdateRoleOutDTO execute(UpdateRoleInDTO in) {

		// 查询角色
		TRole role = roleMapper.selectByPrimaryKey(in.getRoleId());
		if(role == null) {
			throw new IBusinessException(String.format("角色[%s]不存在!", in.getRoleId()));
		}

		// 更新角色
		role.setRoleName(in.getRoleName());
		role.setRemarks(in.getRemarks());
		role.setIsEnabled(in.getIsEnabled());
		role.setUpdateTime(new Date());
		role.setVersion(role.getVersion()+1);
		roleMapper.updateByPrimaryKey(role);

		// 返回数据
		UpdateRoleOutDTO out = new UpdateRoleOutDTO();
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
