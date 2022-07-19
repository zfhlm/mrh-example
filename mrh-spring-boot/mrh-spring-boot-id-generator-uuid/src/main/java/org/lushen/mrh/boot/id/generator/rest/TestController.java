package org.lushen.mrh.boot.id.generator.rest;

import org.lushen.mrh.boot.id.generator.config.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 * 
 * @author hlm
 */
@RestController
public class TestController {

	@Autowired
	private IdGenerator<String> idGenerator;

	@RequestMapping(path="welcome")
	public String welcome() {
		return idGenerator.generate();
	}

}
