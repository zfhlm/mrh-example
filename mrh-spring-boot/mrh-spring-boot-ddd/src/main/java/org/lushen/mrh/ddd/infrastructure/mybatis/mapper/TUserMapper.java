package org.lushen.mrh.ddd.infrastructure.mybatis.mapper;

import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUser;

public interface TUserMapper {

	int insert(TUser record);

	int updateByPrimaryKey(TUser record);

	int deleteByPrimaryKey(Integer userId);

	TUser selectByPrimaryKey(Integer userId);

}