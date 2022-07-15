package org.lushen.mrh.ddd.facade;

import org.lushen.mrh.ddd.application.RoleApplication;
import org.lushen.mrh.ddd.application.role.dto.RegisterRoleReq;
import org.lushen.mrh.ddd.application.role.dto.RegisterRoleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 角色接口
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/role")
public class RoleFacade {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RoleApplication roleApplication;

	@GetMapping(path="register")
	public void register() throws Exception {

		RegisterRoleReq req = new RegisterRoleReq();
		req.setRoleName("测试角色");
		req.setRemarks("该角色只是测试用途");
		req.setIsEnabled(Boolean.TRUE);
		RegisterRoleRes res = roleApplication.registerRole(req);

		System.err.println(objectMapper.writeValueAsString(res));
	}

}
