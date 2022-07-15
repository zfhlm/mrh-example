package org.lushen.mrh.ddd.facade;

import java.util.Arrays;

import org.lushen.mrh.ddd.application.UserApplication;
import org.lushen.mrh.ddd.application.user.dto.FindByUserIdReq;
import org.lushen.mrh.ddd.application.user.dto.FindByUserIdRes;
import org.lushen.mrh.ddd.application.user.dto.RegisterUserWithRoleReq;
import org.lushen.mrh.ddd.application.user.dto.RegisterUserWithRoleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用户接口
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/user")
public class UserFacade {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserApplication userApplication;

	@GetMapping(path="query/{id}")
	public void query(@PathVariable(name="id") Integer userId) throws Exception {

		FindByUserIdReq req = new FindByUserIdReq();
		req.setUserId(userId);
		FindByUserIdRes res = userApplication.findByUserId(req);

		System.err.println(objectMapper.writeValueAsString(res));
	}

	@GetMapping(path="register")
	public void registerRole() throws Exception {

		RegisterUserWithRoleReq req = new RegisterUserWithRoleReq();
		req.setName("李四");
		req.setRoleIds(Arrays.asList(778428811));
		RegisterUserWithRoleRes res = userApplication.registerWithRole(req);

		System.err.println(objectMapper.writeValueAsString(res));
	}

}
