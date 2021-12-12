package org.lushen.mrh.example.seata.service;

import org.lushen.mrh.example.seata.dao.mapper.SeataTwoMapper;
import org.lushen.mrh.example.seata.dao.model.SeataTwo;
import org.lushen.mrh.example.seata.two.TwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwoServiceImpl implements TwoService {

	@Autowired
	private SeataTwoMapper twoMapper;

	@Override
	public void test() {

		SeataTwo seataTwo = new SeataTwo();
		seataTwo.setId((int) (System.currentTimeMillis()/1000));
		seataTwo.setName("zhangsan");
		twoMapper.insert(seataTwo);

	}

}
