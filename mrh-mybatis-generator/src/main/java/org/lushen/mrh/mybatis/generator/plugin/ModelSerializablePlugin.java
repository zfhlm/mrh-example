package org.lushen.mrh.mybatis.generator.plugin;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.springframework.stereotype.Component;

/**
 * model 实现序列化接口
 * 
 * @author hlm
 */
@Component
public class ModelSerializablePlugin extends PluginAdapter {

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

		if(index.getAndIncrement() == 0) {

			// 导入序列化类
			topLevelClass.addImportedType("java.io.Serializable");

			// 添加实现接口
			topLevelClass.addSuperInterface(new FullyQualifiedJavaType("Serializable"));

			// 添加 serialVersionUID 字段
			Field serialVersionUID = new Field("serialVersionUID = 1L", new FullyQualifiedJavaType("long"));
			serialVersionUID.setVisibility(JavaVisibility.PRIVATE);
			serialVersionUID.setStatic(true);
			serialVersionUID.setFinal(true);
			topLevelClass.addField(serialVersionUID);

		}

		return true;
	}

}
