package org.lushen.mrh.ddd;

import org.lushen.mrh.ddd.infrastructure.mybatis.mapper.TUserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 * 
 * @author hlm
 */
@SpringBootApplication
@ComponentScan(basePackageClasses=Application.class)
@MapperScan(basePackageClasses=TUserMapper.class)
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
