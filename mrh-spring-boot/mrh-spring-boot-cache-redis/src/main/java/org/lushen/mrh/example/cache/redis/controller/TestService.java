package org.lushen.mrh.example.cache.redis.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Cacheable(cacheNames="cache.service")
	public String test() {
		System.err.println(getClass());
		return "test";
	}

}
