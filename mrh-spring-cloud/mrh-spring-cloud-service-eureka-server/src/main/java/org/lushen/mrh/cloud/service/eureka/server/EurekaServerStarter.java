package org.lushen.mrh.cloud.service.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@SpringBootApplication
@ComponentScan(basePackageClasses=EurekaServerStarter.class)
@EnableEurekaServer
public class EurekaServerStarter {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerStarter.class, args);
	}

	@Configuration
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
			http.csrf().disable();
			http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
		}
	}

}
