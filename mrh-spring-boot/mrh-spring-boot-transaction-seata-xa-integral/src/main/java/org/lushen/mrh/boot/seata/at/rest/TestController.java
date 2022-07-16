package org.lushen.mrh.boot.seata.at.rest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.lushen.mrh.boot.seata.at.dao.mapper.TIntegralMapper;
import org.lushen.mrh.boot.seata.at.dao.model.TIntegral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

@RestController
public class TestController {

	@Autowired
	private TIntegralMapper integralMapper;

	@Transactional(rollbackFor=Throwable.class)
	@GlobalTransactional(rollbackFor=Throwable.class, timeoutMills=5000)
	@RequestMapping(path="integral")
	public String user() {

		System.err.println(RootContext.getXID());

		// 本地插入
		TIntegral integral = new TIntegral();
		integral.setId(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		integral.setName(UUID.randomUUID().toString());
		integralMapper.insert(integral);

		// 模拟错误回滚
		if(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE)%2 == 0) {
			throw new RuntimeException("test");
		}

		return "success";
	}

}
