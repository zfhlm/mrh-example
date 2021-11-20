package org.lushen.mrh.ddd.action.user;

import org.lushen.mrh.ddd.action.user.dto.FindUserByIdOutDTO;
import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TUserMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 根据 userId 查询用户 action
 * 
 * @author hlm
 */
@Component
public class FindUserByIdAction implements IAction<Integer, FindUserByIdOutDTO> {

	@Autowired
	private TUserMapper userMapper;

	@Override
	public FindUserByIdOutDTO execute(Integer userId) {

		// 查询用户
		TUser record = userMapper.selectByPrimaryKey(userId);

		if(record == null) {
			return null;
		}

		// 返回数据
		FindUserByIdOutDTO out = new FindUserByIdOutDTO();
		out.setUserId(record.getUserId());
		out.setName(record.getName());
		out.setCreateTime(record.getCreateTime());
		out.setCreateUser(record.getCreateUser());
		out.setUpdateTime(record.getUpdateTime());
		out.setUpdateUser(record.getUpdateUser());

		return out;
	}

}
