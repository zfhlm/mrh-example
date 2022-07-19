package org.lushen.mrh.boot.id.generator.rest;

import org.lushen.mrh.id.generator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("revisionIdGenerator")
	private IdGenerator revisionIdGenerator;

	@Autowired
	@Qualifier("segmentIdGenerator")
	private IdGenerator segmentIdGenerator;

	@RequestMapping(path="revision")
	public Long revision() {
		return revisionIdGenerator.generate();
	}

	@RequestMapping(path="segment")
	public Long segment() {
		return segmentIdGenerator.generate();
	}

}
