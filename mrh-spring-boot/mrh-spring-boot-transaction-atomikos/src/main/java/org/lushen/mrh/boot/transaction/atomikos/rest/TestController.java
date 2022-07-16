package org.lushen.mrh.boot.transaction.atomikos.rest;

import org.lushen.mrh.boot.transaction.atomikos.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@GetMapping(path="query")
	public void query() {
		testService.query();
	}

	@GetMapping(path="add")
	public void add() throws Exception {
		testService.add();
	}

}
