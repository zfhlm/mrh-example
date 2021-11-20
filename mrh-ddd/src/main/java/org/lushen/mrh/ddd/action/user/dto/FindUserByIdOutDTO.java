package org.lushen.mrh.ddd.action.user.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserByIdOutDTO {

	// 主键ID
	private Integer userId;

	// 名称
	private String name;

	// 创建时间
	private Date createTime;

	// 创建用户
	private String createUser;

	// 修改时间
	private Date updateTime;

	// 修改用户
	private String updateUser;

}
