package org.lushen.mrh.boot.seata.at.rest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.lushen.mrh.boot.seata.at.dao.mapper.TUserMapper;
import org.lushen.mrh.boot.seata.at.dao.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

@RestController
public class TestController {

	@Autowired
	private TUserMapper userMapper;

	@GlobalTransactional(rollbackFor=Throwable.class, timeoutMills=5000)
	@Transactional
	@RequestMapping(path="user")
	public String user() {

		System.err.println(RootContext.getXID());

		// 本地插入
		TUser user = new TUser();
		user.setId(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		user.setName(UUID.randomUUID().toString());
		userMapper.insert(user);

		// 远程调用模拟，带上 XID
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> res = template.getForEntity("http://localhost:8888/add", String.class);
		Integer id = Integer.parseInt(res.getBody());
		System.out.println(id);

		// 模拟错误回滚
		if(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE)%2 == 0) {
			throw new RuntimeException("test");
		}

		return "success";
	}

}
