package org.lushen.mrh.boot.transaction.rocketmq;

import org.lushen.mrh.boot.transaction.rocketmq.dao.mapper.TUserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackageClasses=Application.class)
@EnableTransactionManagement
@MapperScan(basePackageClasses=TUserMapper.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
