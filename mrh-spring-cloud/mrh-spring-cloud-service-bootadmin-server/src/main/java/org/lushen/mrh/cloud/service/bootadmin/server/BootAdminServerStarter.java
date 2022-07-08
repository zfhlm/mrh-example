package org.lushen.mrh.cloud.service.bootadmin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
@ComponentScan(basePackageClasses=BootAdminServerStarter.class)
public class BootAdminServerStarter {

	public static void main(String[] args) {
		SpringApplication.run(BootAdminServerStarter.class, args);
	}

}
