package org.lushen.mrh.boot.id.generator.config;

import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * snowflake 生成器配置
 * 
 * @author hlm
 */
@Configuration
public class SnowflakeConfiguration {

	/**
	 * 测试，随便使用随机数
	 */
	@Bean
	public SnowflakeIdGenerator snowflakeIdGenerator() throws Exception {
		long epochAt = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01").getTime();
		int dataCenterId = ThreadLocalRandom.current().nextInt((int)SnowflakeIdGenerator.maxCenterId);
		int workerId = ThreadLocalRandom.current().nextInt((int)SnowflakeIdGenerator.maxWorkerId);
		return new SnowflakeIdGenerator(epochAt, dataCenterId, workerId);
	}

}
