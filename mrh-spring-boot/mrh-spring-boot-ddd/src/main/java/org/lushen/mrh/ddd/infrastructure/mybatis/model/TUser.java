package org.lushen.mrh.ddd.infrastructure.mybatis.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表
 * 
 * @author hlm
 */
@Getter
@Setter
public class TUser implements Serializable {

	private static final long serialVersionUID = 1L;

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

	// 版本号
	private Integer version;

}