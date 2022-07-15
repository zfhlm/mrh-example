package org.lushen.mrh.ddd.action.user;

import java.util.Date;

import org.lushen.mrh.ddd.action.user.dto.SaveUserInDTO;
import org.lushen.mrh.ddd.action.user.dto.SaveUserOutDTO;
import org.lushen.mrh.ddd.infrastructure.basic.IAction;
import org.lushen.mrh.ddd.infrastructure.basic.IdGenerator;
import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TUserMapper;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注册用户 action
 * 
 * @author hlm
 */
@Component
public class SaveUserAction implements IAction<SaveUserInDTO, SaveUserOutDTO> {

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private TUserMapper userMapper;

	@Override
	public SaveUserOutDTO execute(SaveUserInDTO in) {

		// 保存用户
		TUser record = new TUser();
		record.setUserId(idGenerator.generate());
		record.setName(in.getName());
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setVersion(0);
		userMapper.insert(record);

		// 返回数据
		SaveUserOutDTO out = new SaveUserOutDTO();
		out.setUserId(record.getUserId());
		out.setName(record.getName());
		out.setCreateTime(record.getCreateTime());
		out.setCreateUser(record.getCreateUser());
		out.setUpdateTime(record.getUpdateTime());
		out.setUpdateUser(record.getUpdateUser());

		return out;
	}

}
