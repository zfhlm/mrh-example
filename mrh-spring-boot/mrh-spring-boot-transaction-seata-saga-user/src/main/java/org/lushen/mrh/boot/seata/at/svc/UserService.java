package org.lushen.mrh.boot.seata.at.svc;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.lushen.mrh.boot.seata.at.dao.mapper.TUserMapper;
import org.lushen.mrh.boot.seata.at.dao.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务
 * 
 * @author hlm
 */
@Service("userService")
public class UserService {

	@Autowired
	private TUserMapper userMapper;

	/**
	 * 提升用户等级
	 * 
	 * @return
	 */
	@Transactional
	public Integer upLevel() {

		TUser user = new TUser();
		user.setId(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		user.setName(UUID.randomUUID().toString());
		userMapper.insert(user);

		System.out.println("up level :: id = " + user.getId());

		// 模拟错误回滚
		if(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE)%2 == 0) {
			throw new RuntimeException("test");
		}

		return user.getId();
	}

	/**
	 * 撤销用户等级
	 * 
	 * @return
	 */
	@Transactional
	public void downLevel(Integer id) {

		System.out.println("down level :: id = " + id);

		userMapper.deleteByPrimaryKey(id);
		
	}

}
