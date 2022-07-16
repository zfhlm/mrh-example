package org.lushen.mrh.boot.transaction.atomikos.service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.lushen.mrh.boot.transaction.atomikos.dao.integral.mapper.TIntegralMapper;
import org.lushen.mrh.boot.transaction.atomikos.dao.integral.model.TIntegral;
import org.lushen.mrh.boot.transaction.atomikos.dao.user.mapper.TUserMapper;
import org.lushen.mrh.boot.transaction.atomikos.dao.user.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

	@Autowired
	private TUserMapper userMapper;
	@Autowired
	private TIntegralMapper integralMapper;

	@Transactional
	public void add() throws Exception {

		TUser user = new TUser();
		user.setId(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		user.setName(UUID.randomUUID().toString());
		userMapper.insert(user);

		TIntegral integral = new TIntegral();
		integral.setId(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		integral.setName(UUID.randomUUID().toString());
		integralMapper.insert(integral);

		if(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE)%2 == 0) {
			throw new Exception("test");
		}

	}

	public void query() {

		userMapper.selects().forEach(System.out::println);

		integralMapper.selects().forEach(System.out::println);

	}

}
