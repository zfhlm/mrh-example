package org.lushen.mrh.boot.seata.at.rest;

import java.util.UUID;

import org.lushen.mrh.boot.seata.at.dao.mapper.TIntegralMapper;
import org.lushen.mrh.boot.seata.at.dao.model.TIntegral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;

@RestController
public class TestController implements TestTccAction {

	@Autowired
	private TIntegralMapper integralMapper;

	@Transactional
	@RequestMapping(path="integral")
	@Override
	public String prepareTcc(TestTccParameter parameter) {

		System.err.println(RootContext.getXID());
		System.out.println("prepare :: " + parameter);

		// 一般进行预处理，这里测试直接插入数据

		// 本地插入
		TIntegral integral = new TIntegral();
		integral.setId(parameter.getId());
		integral.setName(UUID.randomUUID().toString());
		integralMapper.insert(integral);

		return "success";
	}

	@Override
	public void commitTcc(BusinessActionContext context) {
		TestTccParameter parameter = context.getActionContext("parameter", TestTccParameter.class);
		System.out.println("commit :: " + parameter);
	}

	@Override
	public void cancelTcc(BusinessActionContext context) {
		TestTccParameter parameter = context.getActionContext("parameter", TestTccParameter.class);
		System.out.println("cancel :: " + parameter);
		integralMapper.deleteByPrimaryKey(parameter.getId());
	}

}
