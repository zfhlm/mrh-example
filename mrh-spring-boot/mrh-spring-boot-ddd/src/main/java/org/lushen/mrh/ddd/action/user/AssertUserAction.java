package org.lushen.mrh.ddd.action.user;

import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.basic.IBusinessException;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TUserMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 断言已存在用户 action
 * 
 * @author hlm
 */
@Component
public class AssertUserAction extends IAction.IVoidAction<Integer> {

	@Autowired
	private TUserMapper userMapper;

	@Override
	protected void execute0(Integer userId) {

		// 查询用户
		TUser record = userMapper.selectByPrimaryKey(userId);
		if(record == null) {
			throw new IBusinessException(String.format("用户[%s]不存在!", userId));
		}

	}

}
