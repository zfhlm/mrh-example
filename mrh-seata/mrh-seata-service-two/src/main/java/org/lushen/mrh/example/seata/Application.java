package org.lushen.mrh.example.seata;

import org.lushen.mrh.example.seata.dao.mapper.SeataTwoMapper;
import org.lushen.mrh.example.seata.one.PackageOfOne;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackageClasses=Application.class)
@MapperScan(basePackageClasses=SeataTwoMapper.class)
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses=PackageOfOne.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
