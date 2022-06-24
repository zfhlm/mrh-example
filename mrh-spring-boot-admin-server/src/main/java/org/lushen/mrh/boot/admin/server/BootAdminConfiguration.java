package org.lushen.mrh.boot.admin.server;

import java.net.URI;
import java.util.Optional;
import java.util.function.BiFunction;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import de.codecentric.boot.admin.server.cloud.discovery.ServiceInstanceConverter;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.domain.values.Registration;

@Configuration
public class BootAdminConfiguration {

	@Autowired
	private AdminServerProperties properties;

	/**
	 * 权限信息配置
	 */
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

	/**
	 * 应用配置了根路径会导致 springbootadmin 读取监控信息错误，从服务注册信息 metadata 中读取 context-path，添加到 actuator 根路径
	 */
	@Bean
	public BeanPostProcessor serviceInstanceBeanPostProcessor() {
		return new BeanPostProcessor() {
			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if(bean instanceof ServiceInstanceConverter) {
					BiFunction<URI, String, URI> rebuildUriFn = (httpUri, contextPath) -> {
						UriBuilder builder = UriComponentsBuilder.fromUri(httpUri);
						builder.replacePath("/");
						builder.path(contextPath);
						builder.path(httpUri.getPath());
						return builder.build();
					};
					return new ServiceInstanceConverter() {
						@Override
						public Registration convert(ServiceInstance instance) {
							Registration registration = ((ServiceInstanceConverter)bean).convert(instance);
							String contextPath = Optional.ofNullable(instance.getMetadata()).map(e -> e.get("context-path")).orElse(null);
							if(StringUtils.isNotBlank(contextPath)) {
								Registration.Builder builder = Registration.builder();
								builder.name(registration.getName());
								builder.managementUrl(rebuildUriFn.apply(URI.create(registration.getManagementUrl()), contextPath).toString());
								builder.healthUrl(rebuildUriFn.apply(URI.create(registration.getHealthUrl()), contextPath).toString());
								builder.serviceUrl(rebuildUriFn.apply(URI.create(registration.getServiceUrl()), contextPath).toString());
								builder.metadata(registration.getMetadata());
								builder.source(registration.getSource());
								return builder.build();
							}
							return registration;
						}
					};
				}
				return bean;
			}
		};
	}

}
