package org.lushen.mrh.ddd.infrastructure.config;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.lushen.mrh.ddd.infrastructure.basic.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主键ID生成器配置
 * 
 * @author hlm
 */
@Configuration
public class IdGeneratorConfiguration {

	private final AtomicInteger idHoder = new AtomicInteger(new Random().nextInt(Integer.MAX_VALUE));

	@Bean
	public IdGenerator idGenerator() {
		return () -> idHoder.getAndIncrement();
	}

}
