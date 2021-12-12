package org.lushen.mrh.example.seata;

import org.lushen.mrh.example.seata.one.PackageOfOne;
import org.lushen.mrh.example.seata.two.PackageOfTwo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses=Application.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses= {PackageOfOne.class, PackageOfTwo.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
