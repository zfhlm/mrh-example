package org.lushen.mrh.cloud.api.admin;

import org.lushen.mrh.cloud.reference.gateway.GatewayPermissionEvent;
import org.lushen.mrh.cloud.reference.gateway.GatewayStartedEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses=ApplicationStarter.class)
@RemoteApplicationEventScan(basePackageClasses={GatewayStartedEvent.class, GatewayPermissionEvent.class})
public class ApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}

}
