package org.lushen.mrh.example.seata.service;

import org.lushen.mrh.example.seata.dao.mapper.SeataOneMapper;
import org.lushen.mrh.example.seata.dao.model.SeataOne;
import org.lushen.mrh.example.seata.one.OneService;
import org.lushen.mrh.example.seata.two.TwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import io.seata.spring.annotation.GlobalTransactional;

@RestController
public class OneServiceImpl implements OneService {

	@Autowired
	private SeataOneMapper oneMapper;

	@Autowired
	private TwoService twoService;

	@GlobalTransactional
	@Override
	public void test() {

		SeataOne seataOne = new SeataOne();
		seataOne.setId((int) (System.currentTimeMillis()/1000));
		seataOne.setName("zhangsan");
		oneMapper.insert(seataOne);

		twoService.test();

		throw new RuntimeException("回滚异常");

	}

}
