package org.lushen.mrh.ddd.infrastructure.mybatis.mapper;

import org.lushen.mrh.ddd.infrastructure.mybatis.model.TRole;

public interface TRoleMapper {

	int insert(TRole record);

	int updateByPrimaryKey(TRole record);

	int deleteByPrimaryKey(Integer roleId);

	TRole selectByPrimaryKey(Integer roleId);

	TRole selectByRoleName(String roleName);

}