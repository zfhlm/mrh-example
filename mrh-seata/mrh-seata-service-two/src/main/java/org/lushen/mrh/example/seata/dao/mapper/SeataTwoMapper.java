package org.lushen.mrh.example.seata.dao.mapper;

import org.lushen.mrh.example.seata.dao.model.SeataTwo;

public interface SeataTwoMapper {

	int insert(SeataTwo record);

	int updateByPrimaryKey(SeataTwo record);

	int deleteByPrimaryKey(Integer id);

	SeataTwo selectByPrimaryKey(Integer id);

}