package org.lushen.mrh.dubbo.service.demo.impl;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.DubboService;
import org.lushen.mrh.dubbo.reference.DemoService;

@DubboService
public class DemoServiceImpl implements DemoService {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public String sayHello(String message) {
		log.info(message);
		return UUID.randomUUID().toString();
	}

}
