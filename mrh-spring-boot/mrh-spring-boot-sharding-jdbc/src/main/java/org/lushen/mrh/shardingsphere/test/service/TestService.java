package org.lushen.mrh.shardingsphere.test.service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.lushen.mrh.shardingsphere.test.dao.mapper.TUserMapper;
import org.lushen.mrh.shardingsphere.test.dao.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

	private final AtomicInteger idGenerator = new AtomicInteger((int)(System.currentTimeMillis()/1000));

	@Autowired
	private TUserMapper userMapper;

	public TUser query(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public TUser save() {
		
		Integer id = idGenerator.getAndIncrement();
		id = id - new Random().nextInt(id);
		
		TUser user = new TUser();
		user.setUserId(id);
		user.setName(UUID.randomUUID().toString());
		userMapper.insert(user);

		return user;
	}

}
