package org.lushen.mrh.boot.transaction.rabbitmq.rest;

import java.util.UUID;

import org.lushen.mrh.boot.transaction.rabbitmq.dao.mapper.TIntegralMapper;
import org.lushen.mrh.boot.transaction.rabbitmq.dao.model.TIntegral;
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
	@RequestMapping(path="add/{id}")
	public String add(@PathVariable(name="id", required=true) Integer id) {

		System.out.println("add :: " + id);

		if(integralMapper.selectByPrimaryKey(id) == null) {
			TIntegral integral = new TIntegral();
			integral.setId(id);
			integral.setName(UUID.randomUUID().toString());
			integralMapper.insert(integral);
		}

		return "success";
	}

	@Transactional
	@RequestMapping(path="del/{id}")
	public String del(@PathVariable(name="id", required=true) Integer id) {

		integralMapper.deleteByPrimaryKey(id);

		System.out.println("del :: " + id);

		return "success";
	}

}
