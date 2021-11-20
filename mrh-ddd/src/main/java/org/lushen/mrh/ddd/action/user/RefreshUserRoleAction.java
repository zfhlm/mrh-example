package org.lushen.mrh.ddd.action.user;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.lushen.mrh.ddd.action.user.dto.RefreshUserRoleInDTO;
import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.basic.IdGenerator;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TUserRoleMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 刷新用户角色 action
 * 
 * @author hlm
 */
@Component
public class RefreshUserRoleAction extends IAction.IVoidAction<RefreshUserRoleInDTO> {

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private TUserRoleMapper userRoleMapper;

	@Override
	public void execute0(RefreshUserRoleInDTO in) {

		// 查询用户角色
		List<TUserRole> userRoles = userRoleMapper.selectByUserId(in.getUserId());

		// 角色未变更，不进行更新
		Set<Integer> inRoleIds = new HashSet<Integer>(in.getRoleIds());
		Set<Integer> roleIds = userRoles.stream().map(e -> e.getRoleId()).collect(Collectors.toSet());
		if(CollectionUtils.containsAll(inRoleIds, roleIds) && inRoleIds.size() == roleIds.size()) {
			return;
		}

		// 删除用户角色
		for(TUserRole userRole : userRoles) {
			userRoleMapper.deleteByPrimaryKey(userRole.getUserRoleId());
		}

		// 注册用户角色
		for(Integer roleId : in.getRoleIds()) {
			TUserRole userRole = new TUserRole();
			userRole.setUserRoleId(idGenerator.generate());
			userRole.setUserId(in.getUserId());
			userRole.setRoleId(roleId);
			userRole.setCreateTime(new Date());
			userRole.setUpdateTime(new Date());
			userRole.setVersion(0);
			userRoleMapper.insert(userRole);
		}

	}

}
