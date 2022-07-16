package org.lushen.mrh.boot.seata.at.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.xa.DruidXADataSource;

import io.seata.rm.datasource.xa.DataSourceProxyXA;

/**
 * seata 配置
 * 
 * @author hlm
 */
@Configuration
public class SeataConfiguration {

	@Bean("dataSourceProxy")
	@Primary
	public DataSource dataSource(DruidXADataSource dataSource) {
		return new DataSourceProxyXA(dataSource);
	}

}
