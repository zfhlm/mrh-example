package org.lushen.mrh.boot.transaction.atomikos.config;

import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.lushen.mrh.boot.transaction.atomikos.dao.integral.mapper.TIntegralMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.xa.DruidXADataSource;

/**
 * mybatis integral 数据源配置
 * 
 * @author hlm
 */
@Configuration
@MapperScan(basePackageClasses=TIntegralMapper.class, sqlSessionFactoryRef=MybatisIntegralConfiguration.INTEGRAL_SQL_SESSION_FACTORY)
public class MybatisIntegralConfiguration {

	static final String INTEGRAL_SQL_SESSION_FACTORY = "integralSqlSessionFactory";

	@Bean("integralDataSource")
	@ConfigurationProperties("spring.datasource.integral")
	public XADataSource integralDataSource() {
		return new DruidXADataSource();
	}

	@Bean("integralXADataSource")
	public DataSource integralXADataSource(@Qualifier("integralDataSource") XADataSource dataSource) {
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(dataSource);
		return xaDataSource;
	}

	@Bean
	@ConfigurationProperties("mybatis.integral")
	public MybatisProperties integralMybatisProperties() {
		return new MybatisProperties();
	}

	@Bean(INTEGRAL_SQL_SESSION_FACTORY)
	public SqlSessionFactory integralSqlSessionFactory(
			@Qualifier("integralXADataSource") DataSource dataSource,
			@Qualifier("integralMybatisProperties") MybatisProperties properties) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
		factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
		factory.setMapperLocations(properties.resolveMapperLocations());
		return factory.getObject();
	}

}
