package org.lushen.mrh.ddd.action.role;

import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.basic.IBusinessException;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TRoleMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 角色存在断言 action
 * 
 * @author hlm
 */
@Component
public class AssertRoleAction extends IAction.IVoidAction<Integer> {

	@Autowired
	private TRoleMapper roleMapper;

	@Override
	public void execute0(Integer roleId) {

		// 查询角色
		TRole role = roleMapper.selectByPrimaryKey(roleId);
		if(role == null) {
			throw new IBusinessException(String.format("角色[%s]不存在!", roleId));
		}

	}

}
