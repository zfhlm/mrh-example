package org.lushen.mrh.cloud.gateway;

import org.lushen.mrh.cloud.reference.gateway.GatewayPermissionEvent;
import org.lushen.mrh.cloud.reference.gateway.GatewayStartedEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses=GatewayStarter.class)
@RemoteApplicationEventScan(basePackageClasses={GatewayStartedEvent.class, GatewayPermissionEvent.class})
public class GatewayStarter {

	public static void main(String[] args) {
		SpringApplication.run(GatewayStarter.class, args);
	}

}
