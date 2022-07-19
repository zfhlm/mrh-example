package org.lushen.mrh.boot.transaction.rocketmq.dao.mapper;

import org.lushen.mrh.boot.transaction.rocketmq.dao.model.TIntegral;

public interface TIntegralMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(TIntegral record);

	TIntegral selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(TIntegral record);

}