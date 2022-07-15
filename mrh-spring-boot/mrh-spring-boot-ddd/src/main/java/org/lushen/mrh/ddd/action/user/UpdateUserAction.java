package org.lushen.mrh.ddd.action.user;

import java.util.Date;

import org.lushen.mrh.ddd.action.user.dto.UpdateUserInDTO;
import org.lushen.mrh.ddd.action.user.dto.UpdateUserOutDTO;
import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.basic.IBusinessException;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TUserMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新用户 action
 * 
 * @author hlm
 */
@Component
public class UpdateUserAction implements IAction<UpdateUserInDTO, UpdateUserOutDTO> {

	@Autowired
	private TUserMapper userMapper;

	@Override
	public UpdateUserOutDTO execute(UpdateUserInDTO in) {

		// 查询用户
		TUser record = userMapper.selectByPrimaryKey(in.getUserId());
		if(record == null) {
			throw new IBusinessException(String.format("用户[%s]不存在!", in.getUserId()));
		}

		// 更新用户
		record.setName(in.getName());
		record.setUpdateTime(new Date());
		record.setVersion(record.getVersion()+1);
		userMapper.updateByPrimaryKey(record);
		
		// 返回数据
		UpdateUserOutDTO out = new UpdateUserOutDTO();
		out.setUserId(record.getUserId());
		out.setName(record.getName());
		out.setCreateTime(record.getCreateTime());
		out.setCreateUser(record.getCreateUser());
		out.setUpdateTime(record.getUpdateTime());
		out.setUpdateUser(record.getUpdateUser());

		return out;
	}

}
