package org.lushen.mrh.example.springdoc;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="测试接口")
@RestController
@RequestMapping(path="test")
public class TestController {

	@Operation(summary="测试一")
	@PostMapping(path="t")
	public TestRes test(@Validated @RequestBody TestReq req) {
		return new TestRes();
	}

	@Operation(summary="测试二")
	@PostMapping(path="t2")
	public TestRes test2(@Validated @RequestBody TestReq req) {
		return new TestRes();
	}

}
