package org.lushen.mrh.boot.transaction.rabbitmq.dao.mapper;

import org.lushen.mrh.boot.transaction.rabbitmq.dao.model.TUser;

public interface TUserMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(TUser record);

	TUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(TUser record);

}