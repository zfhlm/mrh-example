package org.lushen.mrh.boot.transaction.atomikos.dao.integral.mapper;

import java.util.List;

import org.lushen.mrh.boot.transaction.atomikos.dao.integral.model.TIntegral;

public interface TIntegralMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(TIntegral record);

	TIntegral selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(TIntegral record);

	List<TIntegral> selects();

}