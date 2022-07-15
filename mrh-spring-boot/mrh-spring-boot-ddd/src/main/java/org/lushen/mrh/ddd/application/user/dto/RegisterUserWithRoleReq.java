package org.lushen.mrh.ddd.application.user.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserWithRoleReq {

	// 名称
	private String name;

	// 角色ID
	private List<Integer> roleIds;

}
