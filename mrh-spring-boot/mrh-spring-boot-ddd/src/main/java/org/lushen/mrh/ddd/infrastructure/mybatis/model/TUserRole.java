package org.lushen.mrh.ddd.infrastructure.mybatis.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户角色关联表
 * 
 * @author hlm
 */
@Getter
@Setter
public class TUserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键ID
	private Integer userRoleId;

	// 用户ID
	private Integer userId;

	// 角色ID
	private Integer roleId;

	// 创建时间
	private Date createTime;

	// 创建用户
	private String createUser;

	// 更新时间
	private Date updateTime;

	// 更新用户
	private String updateUser;

	// 版本号
	private Integer version;

}