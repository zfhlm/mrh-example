package org.lushen.mrh.mybatis.generator.plugin;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.springframework.stereotype.Component;

/**
 * model 使用 lombok
 * 
 * @author hlm
 */
@Component
public class ModelLombokPlugin extends PluginAdapter {

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

		// 添加类注释
		if(index.getAndIncrement() == 0) {
			topLevelClass.addImportedType("lombok.Getter");
			topLevelClass.addImportedType("lombok.Setter");
			topLevelClass.addAnnotation("@Getter");
			topLevelClass.addAnnotation("@Setter");
		}

		return true;
	}

	// 不生成 Getter
	@Override
	public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		return false;
	}

	// 不生成 Setter
	@Override
	public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		return false;
	}

}
