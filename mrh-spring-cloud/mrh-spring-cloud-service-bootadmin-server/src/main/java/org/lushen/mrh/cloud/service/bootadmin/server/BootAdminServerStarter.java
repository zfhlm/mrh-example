package org.lushen.mrh.cloud.service.bootadmin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@ComponentScan(basePackageClasses=BootAdminServerStarter.class)
@EnableAdminServer
public class BootAdminServerStarter {

	public static void main(String[] args) {
		SpringApplication.run(BootAdminServerStarter.class, args);
	}

}
