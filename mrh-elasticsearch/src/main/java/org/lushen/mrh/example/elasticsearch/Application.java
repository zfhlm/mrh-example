package org.lushen.mrh.example.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 启动类
 * 
 * @author hlm
 */
@SpringBootApplication
@ComponentScan(basePackageClasses=Application.class)
@EnableElasticsearchRepositories(basePackageClasses=Application.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
