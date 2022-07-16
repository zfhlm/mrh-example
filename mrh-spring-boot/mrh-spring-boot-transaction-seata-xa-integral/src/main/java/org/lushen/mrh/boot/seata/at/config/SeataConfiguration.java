package org.lushen.mrh.boot.seata.at.config;

import org.springframework.context.annotation.Configuration;

/**
 * seata 配置
 * 
 * @author hlm
 */
@Configuration
public class SeataConfiguration {

	// 注意，不能创建代理数据源，否则多次代理会导致回滚错误 
	//	@Bean("dataSourceProxy")
	//	@Primary
	//	@ConditionalOnMissingBean(DataSourceProxyXA.class)
	//	public DataSource dataSource(DruidDataSource dataSource) {
	//		return new DataSourceProxyXA(dataSource);
	//	}

}
