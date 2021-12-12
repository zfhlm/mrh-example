package org.lushen.mrh.example.seata;

import org.lushen.mrh.example.seata.dao.mapper.SeataOneMapper;
import org.lushen.mrh.example.seata.two.PackageOfTwo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackageClasses=Application.class)
@MapperScan(basePackageClasses=SeataOneMapper.class)
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses=PackageOfTwo.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
