package org.lushen.mrh.mybatis.generator.plugin;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.springframework.stereotype.Component;

/**
 * model、field 添加数据库注释
 * 
 * @author hlm
 */
@Component
public class ModelCommentPlugin extends PluginAdapter {

	private final AtomicInteger index = new AtomicInteger(0);

	private final AtomicReference<String> clazzHolder = new AtomicReference<String>();

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

		if( ! StringUtils.equals(clazzHolder.get(), topLevelClass.getType().getFullyQualifiedName()) ) {
			clazzHolder.set(topLevelClass.getType().getFullyQualifiedName());
			index.set(0);
		}

		// model 添加注释
		if(index.getAndIncrement() == 0) {
			StringBuilder builder = new StringBuilder();
			builder.append("/**\n");
			builder.append(" * ").append(introspectedTable.getRemarks()).append("\n");
			builder.append(" * \n");
			builder.append(" * @author ").append(System.getProperty("user.name")).append("\n");
			builder.append(" */");
			topLevelClass.addJavaDocLine(builder.toString());
		}

		// field 添加注释
		if(StringUtils.isNotBlank(introspectedColumn.getRemarks())) {
			field.addJavaDocLine("// " + introspectedColumn.getRemarks());
		}

		return true;
	}

}
