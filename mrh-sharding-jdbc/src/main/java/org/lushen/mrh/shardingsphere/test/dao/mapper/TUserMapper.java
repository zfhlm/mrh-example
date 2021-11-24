package org.lushen.mrh.shardingsphere.test.dao.mapper;

import org.lushen.mrh.shardingsphere.test.dao.model.TUser;

public interface TUserMapper {

	int insert(TUser record);

	int updateByPrimaryKey(TUser record);

	int deleteByPrimaryKey(Integer userId);

	TUser selectByPrimaryKey(Integer userId);

}