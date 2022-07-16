package org.lushen.mrh.boot.transaction.atomikos.config;

import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.lushen.mrh.boot.transaction.atomikos.dao.user.mapper.TUserMapper;
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
 * mybatis user 数据源配置
 * 
 * @author hlm
 */
@Configuration
@MapperScan(basePackageClasses=TUserMapper.class, sqlSessionFactoryRef=MybatisUserConfiguration.USER_SQL_SESSION_FACTORY)
public class MybatisUserConfiguration {

	static final String USER_SQL_SESSION_FACTORY = "userSqlSessionFactory";

	@Bean("userDataSource")
	@ConfigurationProperties("spring.datasource.user")
	public XADataSource userDataSource() {
		return new DruidXADataSource();
	}

	@Bean("userXADataSource")
	public DataSource userXADataSource(@Qualifier("userDataSource") XADataSource dataSource) {
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(dataSource);
		return xaDataSource;
	}

	@Bean("userMybatisProperties")
	@ConfigurationProperties("mybatis.user")
	public MybatisProperties userMybatisProperties() {
		return new MybatisProperties();
	}

	@Bean(USER_SQL_SESSION_FACTORY)
	public SqlSessionFactory userSqlSessionFactory(
			@Qualifier("userXADataSource") DataSource dataSource,
			@Qualifier("userMybatisProperties") MybatisProperties properties) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
		factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
		factory.setMapperLocations(properties.resolveMapperLocations());
		return factory.getObject();
	}

}
