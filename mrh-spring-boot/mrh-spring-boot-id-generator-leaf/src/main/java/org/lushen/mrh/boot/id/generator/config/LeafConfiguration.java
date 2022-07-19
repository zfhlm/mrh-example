package org.lushen.mrh.boot.id.generator.config;

import org.lushen.mrh.boot.id.generator.define.SegmentIdGenerator;
import org.lushen.mrh.boot.id.generator.define.SnowflakeIdGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * leaf 配置
 * 
 * @author hlm
 */
@Configuration
public class LeafConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@ConfigurationProperties("leaf")
	public LeafServerProperties leafServerProperties() {
		return new LeafServerProperties();
	}

	@Bean
	public SnowflakeIdGenerator snowflakeIdGenerator(RestTemplate restTemplate, LeafServerProperties properties) {
		return () -> restTemplate.getForObject(properties.getSnowflakeUrl(), Long.class);
	}

	@Bean
	public SegmentIdGenerator segmentIdGenerator(RestTemplate restTemplate, LeafServerProperties properties) {
		return () -> restTemplate.getForObject(properties.getSegmentUrl(), Long.class);
	}

}
