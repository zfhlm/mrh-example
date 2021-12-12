package org.lushen.mrh.example.seata.api;

import org.lushen.mrh.example.seata.one.OneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="at")
public class AtController {
	
	@Autowired
	private OneService oneService;

	@GetMapping(path="test")
	public String test() {
		oneService.test();
		return "success";
	}

}
