package org.lushen.mrh.ddd.action.user.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshUserRoleInDTO {

	// 主键ID
	private Integer userId;

	// 角色ID
	private List<Integer> roleIds;

}
