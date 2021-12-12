package org.lushen.mrh.example.seata.dao.mapper;

import org.lushen.mrh.example.seata.dao.model.SeataOne;

public interface SeataOneMapper {

	int insert(SeataOne record);

	int updateByPrimaryKey(SeataOne record);

	int deleteByPrimaryKey(Integer id);

	SeataOne selectByPrimaryKey(Integer id);

}