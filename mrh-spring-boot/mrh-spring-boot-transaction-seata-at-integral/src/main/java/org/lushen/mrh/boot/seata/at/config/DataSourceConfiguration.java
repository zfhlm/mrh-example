package org.lushen.mrh.boot.seata.at.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.seata.rm.datasource.DataSourceProxy;

@Configuration
public class DataSourceConfiguration {

	@Bean
	@ConfigurationProperties("spring.datasource.dbcp2")
	public BasicDataSource basicDataSource(DataSourceProperties properties) {
		return (BasicDataSource) properties.initializeDataSourceBuilder().build();
	}

	@Bean("dataSourceProxy")
	@Primary
	public DataSourceProxy dataSourceProxy(BasicDataSource dataSource) {
		return new DataSourceProxy(dataSource);
	}

}
