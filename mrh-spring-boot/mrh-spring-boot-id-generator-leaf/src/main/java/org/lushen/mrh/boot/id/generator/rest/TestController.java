package org.lushen.mrh.boot.id.generator.rest;

import org.lushen.mrh.boot.id.generator.define.SegmentIdGenerator;
import org.lushen.mrh.boot.id.generator.define.SnowflakeIdGenerator;
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
	private SnowflakeIdGenerator snowflakeIdGenerator;

	@Autowired
	private SegmentIdGenerator segmentIdGenerator;

	@RequestMapping(path="snowflake")
	public Long snowflake() {
		return snowflakeIdGenerator.generate();
	}

	@RequestMapping(path="segment")
	public Long segment() {
		return segmentIdGenerator.generate();
	}

}
