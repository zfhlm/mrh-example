package org.lushen.mrh.shardingsphere.test.controller;

import org.lushen.mrh.shardingsphere.test.dao.model.TUser;
import org.lushen.mrh.shardingsphere.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class WelcomeController {

	@Autowired
	private TestService testService;

	@GetMapping(path="/")
	public String index() {
		return "success";
	}

	@GetMapping(path="/user/query/{id}")
	public TUser query(@PathVariable(name="id") Integer id) {
		return testService.query(id);
	}

	@GetMapping(path="/user/save")
	public TUser save() {
		return testService.save();
	}

}
