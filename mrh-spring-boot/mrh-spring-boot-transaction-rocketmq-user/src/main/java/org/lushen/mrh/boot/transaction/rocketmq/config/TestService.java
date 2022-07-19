package org.lushen.mrh.boot.transaction.rocketmq.config;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.transaction.rocketmq.dao.mapper.TUserMapper;
import org.lushen.mrh.boot.transaction.rocketmq.dao.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private TUserMapper userMapper;

	@Transactional
	public void add(TestPayload payload) {

		log.info("Add :: " + payload);

		TUser record = new TUser();
		record.setId(payload.getId());
		record.setName(payload.getName());
		userMapper.insert(record);

		if(ThreadLocalRandom.current().nextInt(10) > 5) {
			throw new RuntimeException("add fail :: " + payload);
		}

	}

	public boolean check(TestPayload payload) {
		return userMapper.selectByPrimaryKey(payload.getId()) != null;
	}

}
