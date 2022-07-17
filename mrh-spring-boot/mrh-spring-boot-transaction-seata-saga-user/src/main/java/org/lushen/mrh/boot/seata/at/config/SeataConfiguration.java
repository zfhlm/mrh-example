package org.lushen.mrh.boot.seata.at.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.seata.rm.datasource.DataSourceProxy;

/**
 * seata 配置
 * 
 * @author hlm
 */
@Configuration
public class SeataConfiguration {

	// 代理数据源
	@Bean("dataSourceProxy")
	@Primary
	public DataSourceProxy dataSourceProxy(BasicDataSource dataSource) {
		return new DataSourceProxy(dataSource);
	}

}
