package org.lushen.mrh.ddd.infrastructure.mybatis.mapper;

import java.util.List;

import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUserRole;

public interface TUserRoleMapper {

	int deleteByPrimaryKey(Integer userRoleId);

	int insert(TUserRole record);

	TUserRole selectByPrimaryKey(Integer userRoleId);

	int updateByPrimaryKey(TUserRole record);

	List<TUserRole> selectByRoleId(Integer roleId);

	List<TUserRole> selectByUserId(Integer userId);

}