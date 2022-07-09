package org.lushen.mrh.boot.mybatis.generator.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 配置项
 * 
 * @author hlm
 */
public class MybatisProperties implements InitializingBean {

	@NotNull
	private JdbcProperties jdbc;

	@NotNull
	private ContextProperties context;

	public JdbcProperties getJdbc() {
		return jdbc;
	}

	public void setJdbc(JdbcProperties jdbc) {
		this.jdbc = jdbc;
	}

	public ContextProperties getContext() {
		return context;
	}

	public void setContext(ContextProperties context) {
		this.context = context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LogFactory.getLog(getClass()).info(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(jdbc);
		builder.append(context);
		return builder.toString();
	}

	public static class ContextProperties {

		//存放路径
		@NotBlank
		private String projectPath = new File(FileSystemView.getFileSystemView().getHomeDirectory(), "/mybatis").getPath();

		//model包名
		@NotBlank
		private String modelPackage;

		//mapper包名
		@NotBlank
		private String mapperPackage;

		//mapping包名
		@NotBlank
		private String mappingPackage;

		// 指定表
		@NotNull
		private List<String> tables = new ArrayList<String>();

		public String getProjectPath() {
			return projectPath;
		}

		public void setProjectPath(String projectPath) {
			this.projectPath = projectPath;
		}

		public String getModelPackage() {
			return modelPackage;
		}

		public void setModelPackage(String modelPackage) {
			this.modelPackage = modelPackage;
		}

		public String getMapperPackage() {
			return mapperPackage;
		}

		public void setMapperPackage(String mapperPackage) {
			this.mapperPackage = mapperPackage;
		}

		public String getMappingPackage() {
			return mappingPackage;
		}

		public void setMappingPackage(String mappingPackage) {
			this.mappingPackage = mappingPackage;
		}

		public List<String> getTables() {
			return tables;
		}

		public void setTables(List<String> tables) {
			this.tables = tables;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("\nContext [\n\tprojectPath=");
			builder.append(projectPath);
			builder.append(", \n\tmodelPackage=");
			builder.append(modelPackage);
			builder.append(", \n\tmapperPackage=");
			builder.append(mapperPackage);
			builder.append(", \n\tmappingPackage=");
			builder.append(mappingPackage);
			builder.append(", \n\ttables=");
			builder.append(tables);
			builder.append("\n]");
			return builder.toString();
		}

	}

	public static class JdbcProperties {

		@NotBlank
		private String driverLibraryPath;

		@NotBlank
		private String driverClass;

		@NotBlank
		private String url;

		@NotBlank
		private String user;

		@NotBlank
		private String password;

		public String getDriverClass() {
			return driverClass;
		}

		public void setDriverClass(String driverClass) {
			this.driverClass = driverClass;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDriverLibraryPath() {
			return driverLibraryPath;
		}

		public void setDriverLibraryPath(String driverLibraryPath) {
			this.driverLibraryPath = driverLibraryPath;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("\nJdbc [\n\tdriverClass=");
			builder.append(driverClass);
			builder.append(", \n\tdriverLibraryPath=");
			builder.append(driverLibraryPath);
			builder.append(", \n\turl=");
			builder.append(url);
			builder.append(", \n\tuser=");
			builder.append(user);
			builder.append(", \n\tpassword=");
			builder.append(password);
			builder.append("\n]");
			return builder.toString();
		}

	}

}
