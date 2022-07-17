package org.lushen.mrh.boot.seata.at.rest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.lushen.mrh.boot.seata.at.dao.mapper.TIntegralMapper;
import org.lushen.mrh.boot.seata.at.dao.model.TIntegral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private TIntegralMapper integralMapper;

	@Transactional
	@RequestMapping(path="add")
	public String add() {

		TIntegral integral = new TIntegral();
		integral.setId(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		integral.setName(UUID.randomUUID().toString());
		integralMapper.insert(integral);

		System.out.println("add :: " + integral.getId());

		return String.valueOf(integral.getId());
	}

	@Transactional
	@RequestMapping(path="del/{id}")
	public String del(@PathVariable(name="id", required=true) Integer id) {

		integralMapper.deleteByPrimaryKey(id);

		System.out.println("del :: " + id);

		return String.valueOf(id);
	}

}
