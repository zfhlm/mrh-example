package org.lushen.mrh.ddd.action.role.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindRoleByIdOutDTO {

	// 角色ID
	private Integer roleId;

	// 角色名称
	private String roleName;

	// 角色描述
	private String remarks;

	// 是否启用状态
	private Boolean isEnabled;

	// 创建时间
	private Date createTime;

	// 创建用户
	private String createUser;

	// 更新时间
	private Date updateTime;

	// 更新用户
	private String updateUser;

}
