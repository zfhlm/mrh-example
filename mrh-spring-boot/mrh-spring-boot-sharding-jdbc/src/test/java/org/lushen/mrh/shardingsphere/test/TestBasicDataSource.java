package org.lushen.mrh.shardingsphere.test;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.handler.IgnoreErrorsBindHandler;
import org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

public class TestBasicDataSource {
	
	public static void main(String[] args) {
		
		Properties properties = new Properties();
		
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName(null);
		dataSource.setUrl(null);
		dataSource.setUsername(null);
		dataSource.setPassword(null);

		Bindable<BasicDataSource> target = Bindable.ofInstance(dataSource);
		BindHandler bindHandler = new IgnoreErrorsBindHandler(new IgnoreTopLevelConverterNotFoundBindHandler());
		Binder binder = new Binder(new MapConfigurationPropertySource(properties));
		binder.bind("", target, bindHandler);
		
	}

}
