package org.lushen.mrh.mybatis.generator.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.mybatis.generator.config.MybatisProperties;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class Generator implements InitializingBean {

	private final Log log = LogFactory.getLog("mybatis-generator");

	@Autowired
	private MybatisProperties properties;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {

		// 清除历史文件
		File root = new File(this.properties.getContext().getProjectPath());
		if(root.exists()) {
			try {
				log.info("清除历史生成文件...");
				FileUtils.deleteDirectory(root);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		root.mkdirs();

		// 加载驱动配置
		List<String> entrys = new ArrayList<String>();
		for(File file : new DefaultResourceLoader().getResource(this.properties.getJdbc().getDriverLibraryPath()).getFile().listFiles()) {
			log.info("加载驱动:" + file.getPath());
			entrys.add(file.getPath());
		}

		// 注释配置
		log.info("初始化注释配置...");
		CommentGeneratorConfiguration cmtGenCfg = new CommentGeneratorConfiguration();
		cmtGenCfg.addProperty("suppressAllComments", "true");

		// 数据库配置
		log.info("初始化数据库连接配置...");
		JDBCConnectionConfiguration jdbcConnCfg = new JDBCConnectionConfiguration();
		jdbcConnCfg.setDriverClass(this.properties.getJdbc().getDriverClass());
		jdbcConnCfg.setConnectionURL(this.properties.getJdbc().getUrl());
		jdbcConnCfg.setUserId(this.properties.getJdbc().getUser());
		jdbcConnCfg.setPassword(this.properties.getJdbc().getPassword());

		// 类型配置
		log.info("初始化Java类型配置...");
		JavaTypeResolverConfiguration javaTypeResCfg = new JavaTypeResolverConfiguration();
		javaTypeResCfg.addProperty("forceBigDecimals", "false");

		// 生成实体配置
		log.info("初始化model配置...");
		JavaModelGeneratorConfiguration javaModelGenCfg = new JavaModelGeneratorConfiguration();
		javaModelGenCfg.setTargetPackage(this.properties.getContext().getModelPackage());
		javaModelGenCfg.setTargetProject(this.properties.getContext().getProjectPath());
		javaModelGenCfg.addProperty("enableSubPackages", "true");
		javaModelGenCfg.addProperty("trimStrings", "false");

		// 生成映射文件配置
		log.info("初始化mapping配置...");
		SqlMapGeneratorConfiguration sqlMapGenCfg = new SqlMapGeneratorConfiguration();
		sqlMapGenCfg.setTargetPackage(this.properties.getContext().getMappingPackage());
		sqlMapGenCfg.setTargetProject(this.properties.getContext().getProjectPath());
		sqlMapGenCfg.addProperty("enableSubPackages", "true");

		// 生成接口配置
		log.info("初始化mapper配置...");
		JavaClientGeneratorConfiguration javaClientGenCfg = new JavaClientGeneratorConfiguration();
		javaClientGenCfg.setConfigurationType("XMLMAPPER");
		javaClientGenCfg.setTargetPackage(this.properties.getContext().getMapperPackage());
		javaClientGenCfg.setTargetProject(this.properties.getContext().getProjectPath());
		javaClientGenCfg.addProperty("enableSubPackages", "true");

		// 上下文配置
		log.info("初始化上下文配置...");
		Context context = new Context(null);
		context.setId("MyBatis3");
		context.setTargetRuntime("MyBatis3");
		context.setCommentGeneratorConfiguration(cmtGenCfg);
		context.setJdbcConnectionConfiguration(jdbcConnCfg);
		context.setJavaTypeResolverConfiguration(javaTypeResCfg);
		context.setJavaModelGeneratorConfiguration(javaModelGenCfg);
		context.setSqlMapGeneratorConfiguration(sqlMapGenCfg);
		context.setJavaClientGeneratorConfiguration(javaClientGenCfg);

		// 插件配置
		applicationContext.getBeansOfType(PluginAdapter.class).values().forEach(plugin -> {
			PluginConfiguration pluginConfiguration = new PluginConfiguration();
			pluginConfiguration.setConfigurationType(AopUtils.getTargetClass(plugin).getName());
			context.addPluginConfiguration(pluginConfiguration);
			log.info("加载插件：" + pluginConfiguration.getConfigurationType());
		});

		// 格式化 xml
		applicationContext.getBeansOfType(XmlFormatter.class).values().stream().findFirst().ifPresent(formater -> {
			context.addProperty(PropertyRegistry.CONTEXT_XML_FORMATTER, AopUtils.getTargetClass(formater).getName());
			log.info("加载 xml formater：" + AopUtils.getTargetClass(formater).getName());
		});

		//指定生成表
		for(String tableName : this.properties.getContext().getTables()) {
			log.info("加载指定生成表:" + tableName);
			TableConfiguration tableCfg = new TableConfiguration(context);
			tableCfg.setTableName(tableName);
			tableCfg.setCountByExampleStatementEnabled(false);
			tableCfg.setDeleteByExampleStatementEnabled(false);
			tableCfg.setSelectByExampleStatementEnabled(false);
			tableCfg.setUpdateByExampleStatementEnabled(false);
			tableCfg.addProperty("useActualColumnNames", "false");
			context.addTableConfiguration(tableCfg);
		}

		//生成文件
		log.info("开始生成mybatis代码...");
		Configuration configuration = new Configuration();
		entrys.forEach(entry -> configuration.addClasspathEntry(entry));
		configuration.addContext(context);

		new MyBatisGenerator(configuration, new DefaultShellCallback(true), null).generate(null);

		log.info("完成生成mybatis代码...");
	}

}
