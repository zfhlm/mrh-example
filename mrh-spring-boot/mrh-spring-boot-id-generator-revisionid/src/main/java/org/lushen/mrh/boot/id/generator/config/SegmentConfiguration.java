package org.lushen.mrh.boot.id.generator.config;

import javax.sql.DataSource;

import org.lushen.mrh.id.generator.IdGenerator;
import org.lushen.mrh.id.generator.segment.SegmentIdGeneratorFactory;
import org.lushen.mrh.id.generator.segment.SegmentProperties;
import org.lushen.mrh.id.generator.segment.SegmentRepository;
import org.lushen.mrh.id.generator.segment.achieve.SegmentMysqlJdbcRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * segment 配置
 * 
 * @author hlm
 */
@Configuration
public class SegmentConfiguration {

	@Bean
	@ConfigurationProperties("segment")
	public SegmentProperties segmentProperties() {
		return SegmentProperties.buildDefault();
	}

	@Bean
	public SegmentRepository segmentRepository(DataSource dataSource) {
		return new SegmentMysqlJdbcRepository(dataSource);
	}

	@Bean("segmentIdGenerator")
	public IdGenerator segmentIdGenerator(SegmentRepository repository, SegmentProperties properties) {
		return new SegmentIdGeneratorFactory(repository).create(properties);
	}

}
