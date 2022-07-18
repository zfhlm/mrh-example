package org.lushen.mrh.boot.seata.at.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.seata.core.context.RootContext;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import io.seata.spring.annotation.GlobalTransactional;

@RestController
public class TestController {

	@Autowired
	private StateMachineEngine stateMachineEngine;

	@GlobalTransactional(rollbackFor=Throwable.class, timeoutMills=5000)
	@Transactional
	@RequestMapping(path="user")
	public String user() throws Exception {

		System.err.println(RootContext.getXID());

		Map<String, Object> startParams = new HashMap<String, Object>();
		startParams.put("quantity", ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		StateMachineInstance instance = stateMachineEngine.start("userIntegralService", null, startParams);

		if(instance.getException() != null) {
			throw instance.getException();
		}
		if(instance.getStatus() != ExecutionStatus.SU) {
			throw new RuntimeException("fail");
		}

		return "success";
	}

}
