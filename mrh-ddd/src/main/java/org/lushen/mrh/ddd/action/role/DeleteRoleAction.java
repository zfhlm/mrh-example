package org.lushen.mrh.ddd.action.role;

import java.util.List;

import org.lushen.mrh.ddd.action.role.dto.DeleteRoleInDTO;
import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.basic.IBusinessException;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TRoleMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TUserRoleMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TRole;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注销角色 action
 * 
 * @author hlm
 */
@Component
public class DeleteRoleAction extends IAction.IVoidAction<DeleteRoleInDTO> {

	@Autowired
	private TRoleMapper roleMapper;
	@Autowired
	private TUserRoleMapper userRoleMapper;

	@Override
	public void execute0(DeleteRoleInDTO in) {

		// 查询角色
		TRole role = roleMapper.selectByPrimaryKey(in.getRoleId());
		if(role == null) {
			throw new IBusinessException(String.format("角色[%s]不存在!", in.getRoleId()));
		}

		// 已关联用户
		List<TUserRole> userRoles = userRoleMapper.selectByRoleId(in.getRoleId());
		if( ! userRoles.isEmpty() ) {
			throw new IBusinessException(String.format("角色[%s]已关联用户!", role.getRoleName()));
		}

		// 注销角色
		roleMapper.deleteByPrimaryKey(in.getRoleId());

	}

}
