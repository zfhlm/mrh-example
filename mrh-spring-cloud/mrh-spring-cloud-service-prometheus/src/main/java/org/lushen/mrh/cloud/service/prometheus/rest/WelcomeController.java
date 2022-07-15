package org.lushen.mrh.cloud.service.prometheus.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@RequestMapping(path="api/welcome")
	public String welcome() {
		return "success";
	}

}
