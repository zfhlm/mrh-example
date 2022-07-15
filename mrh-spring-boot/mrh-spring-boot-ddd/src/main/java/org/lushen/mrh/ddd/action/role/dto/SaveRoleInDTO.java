package org.lushen.mrh.ddd.action.role.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveRoleInDTO {

	// 角色名称
	private String roleName;

	// 角色描述
	private String remarks;

	// 是否启用状态
	private Boolean isEnabled;

}
