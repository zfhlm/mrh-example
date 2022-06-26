package org.lushen.mrh.mybatis.generator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * 加载配置项
 * 
 * @author hlm
 */
@Configuration
public class MybatisConfiguration {

	@Bean
	@Validated
	@ConfigurationProperties(prefix="mybatis-generator")
	public MybatisProperties mybatisProperties() {
		return new MybatisProperties();
	}

}
