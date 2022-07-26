package org.lushen.mrh.dubbo.service.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses=ApplicationStarter.class)
@EnableDubbo
public class ApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}

}
