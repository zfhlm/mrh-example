package org.lushen.mrh.ddd.action.role;

import java.util.Date;

import org.lushen.mrh.ddd.action.role.dto.SaveRoleInDTO;
import org.lushen.mrh.ddd.action.role.dto.SaveRoleOutDTO;
import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.basic.IBusinessException;
import org.lushen.mrh.ddd.infrastructure.basic.IdGenerator;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TRoleMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注册角色 action
 * 
 * @author hlm
 */
@Component
public class SaveRoleAction implements IAction<SaveRoleInDTO, SaveRoleOutDTO> {

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private TRoleMapper roleMapper;

	@Override
	public SaveRoleOutDTO execute(SaveRoleInDTO in) {

		// 名称不允许重复
		TRole role = roleMapper.selectByRoleName(in.getRoleName());
		if(role != null) {
			throw new IBusinessException(String.format("角色[%s]已存在!", role.getRoleName()));
		}

		// 保存角色
		TRole record = new TRole();
		record.setRoleId(idGenerator.generate());
		record.setRoleName(in.getRoleName());
		record.setRemarks(in.getRemarks());
		record.setIsEnabled(in.getIsEnabled());
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setVersion(0);
		roleMapper.insert(record);

		// 返回数据
		SaveRoleOutDTO out = new SaveRoleOutDTO();
		out.setRoleId(record.getRoleId());
		out.setRoleName(record.getRoleName());
		out.setRemarks(record.getRemarks());
		out.setIsEnabled(record.getIsEnabled());
		out.setCreateTime(record.getCreateTime());
		out.setCreateUser(record.getCreateUser());
		out.setUpdateTime(record.getUpdateTime());
		out.setUpdateUser(record.getUpdateUser());

		return out;
	}

}
