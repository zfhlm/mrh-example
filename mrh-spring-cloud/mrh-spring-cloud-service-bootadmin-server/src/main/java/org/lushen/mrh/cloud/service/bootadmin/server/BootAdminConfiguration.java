package org.lushen.mrh.cloud.service.bootadmin.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

/**
 * 权限信息配置
 */
@Configuration
public class BootAdminConfiguration {

	@Autowired
	private AdminServerProperties properties;

	@Bean
	public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
		return new WebSecurityConfigurerAdapter() {
			@Override
			protected void configure(HttpSecurity http) throws Exception {
				SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
				successHandler.setTargetUrlParameter("redirectTo");
				http.authorizeRequests()
					.antMatchers(properties.getContextPath() + "/assets/**").permitAll()
					.antMatchers(properties.getContextPath() + "/login").permitAll()
					.anyRequest().authenticated()
					.and().formLogin().loginPage(properties.getContextPath() + "/login").successHandler(successHandler)
					.and().logout().logoutUrl(properties.getContextPath() + "/logout")
					.and().httpBasic()
					.and().csrf().disable();
			}

		};
	}

}
