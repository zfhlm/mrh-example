package org.lushen.mrh.cloud.service.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses=EurekaClientStarter.class)
@EnableDiscoveryClient
@EnableEurekaClient
public class EurekaClientStarter {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientStarter.class, args);
	}

}
