package org.lushen.mrh.cloud.api.mobile;

import org.lushen.mrh.cloud.reference.clients.OrganClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses=ApplicationStarter.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses= {OrganClient.class})
public class ApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}

}
