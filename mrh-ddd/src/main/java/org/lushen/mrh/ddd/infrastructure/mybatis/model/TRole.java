package org.lushen.mrh.ddd.infrastructure.mybatis.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色表
 * 
 * @author hlm
 */
@Getter
@Setter
public class TRole implements Serializable {

	private static final long serialVersionUID = 1L;

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

	// 版本号
	private Integer version;

}