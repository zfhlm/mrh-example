package org.lushen.mrh.boot.seata.at.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

import io.seata.rm.datasource.xa.DataSourceProxyXA;

@Configuration
public class XADataSourceConfiguration {

	@Bean
	@ConfigurationProperties("spring.datasource")
	public DruidDataSource basicDataSource() {
		return new DruidDataSource();
	}

	@Bean("dataSourceProxy")
	@Primary
	public DataSource dataSource(DruidDataSource dataSource) {
		return new DataSourceProxyXA(dataSource);
	}

}
