package org.lushen.mrh.boot.id.generator.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import org.lushen.mrh.id.generator.IdGenerator;
import org.lushen.mrh.id.generator.revision.RevisionIdGeneratorFactory;
import org.lushen.mrh.id.generator.revision.RevisionProperties;
import org.lushen.mrh.id.generator.revision.RevisionRepository;
import org.lushen.mrh.id.generator.revision.achieve.RevisionMysqlJdbcRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * revision 配置
 * 
 * @author hlm
 */
@Configuration
public class RevisionConfiguration {

	// 处理配置无法转换 LocalDate
	@Bean
	@ConfigurationPropertiesBinding
	public Converter<String, LocalDate> localDateConverter() {
		return new Converter<String, LocalDate>() {
			@Override
			public LocalDate convert(String source) {
				return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}
		};
	}

	@Bean
	@ConfigurationProperties("revision")
	public RevisionProperties revisionProperties() {
		return RevisionProperties.buildDefault();
	}

	@Bean
	public RevisionRepository revisionRepository(DataSource dataSource) {
		return new RevisionMysqlJdbcRepository(dataSource);
	}

	@Bean("revisionIdGenerator")
	public IdGenerator revisionIdGenerator(RevisionRepository repository, RevisionProperties properties) {
		return new RevisionIdGeneratorFactory(repository).create(properties);
	}

}
