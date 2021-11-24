package org.lushen.mrh.example.cache.redis.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

	@Cacheable(cacheNames="cache.test1")
	@GetMapping(path="get")
	public TestVo get() {
		TestVo vo = new TestVo();
		vo.setId(1);
		vo.setName("zhangsan");
		return vo;
	}

	@Cacheable(cacheNames="cache.test1", sync=true)
	@GetMapping(path="sync")
	public TestVo sync() {

		System.out.println("go into sync");

		TestVo vo = new TestVo();
		vo.setId(1);
		vo.setName("zhangsan");

		// 尝试阻塞
		try {
			Thread.sleep(20000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("finish sync");

		return vo;
	}

	@CachePut(cacheNames="cache.test2")
	@GetMapping(path="put")
	public TestVo put() {
		TestVo vo = new TestVo();
		vo.setId(1);
		vo.setName("zhangsan");
		return vo;
	}

	@CacheEvict(cacheNames="cache.test3")
	@GetMapping(path="delete")
	public TestVo delete() {
		TestVo vo = new TestVo();
		vo.setId(1);
		vo.setName("zhangsan");
		return vo;
	}

	@CacheEvict(cacheNames="cache.test4", allEntries=true)
	@GetMapping(path="clean")
	public TestVo clean() {
		TestVo vo = new TestVo();
		vo.setId(1);
		vo.setName("zhangsan");
		return vo;
	}

}
