package org.lushen.mrh.boot.transaction.atomikos.dao.user.mapper;

import java.util.List;

import org.lushen.mrh.boot.transaction.atomikos.dao.user.model.TUser;

public interface TUserMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(TUser record);

	TUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(TUser record);

	List<TUser> selects();

}