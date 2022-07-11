package org.lushen.mrh.dubbo.api.demo.rest;

import java.util.UUID;

import org.lushen.mrh.dubbo.reference.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@Autowired
	private DemoService demoService;

	@GetMapping(path="welcome")
	public String sayHello() {
		return demoService.sayHello(UUID.randomUUID().toString());
	}

}
