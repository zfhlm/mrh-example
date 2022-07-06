package org.lushen.mrh.cloud.api.mobile.config;

import org.lushen.mrh.cloud.reference.fallback.DepartmentClientFallbackFactory;
import org.lushen.mrh.cloud.reference.fallback.OrganClientFallbackFactory;
import org.lushen.mrh.cloud.reference.supports.feign.InnerServerErrorBodyDecoderFactory;
import org.lushen.mrh.cloud.reference.supports.feign.ServiceNameCircuitBreakerNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FallbackConfiguration {
	
	@Bean
	public InnerServerErrorBodyDecoderFactory feignInnerServerErrorBodyDecoderFactory() {
		return new InnerServerErrorBodyDecoderFactory();
	}

	@Bean
	public ServiceNameCircuitBreakerNameResolver serviceNameCircuitBreakerNameResolver() {
		return new ServiceNameCircuitBreakerNameResolver();
	}
	
	@Bean
	public OrganClientFallbackFactory organClientFallbackFactory() {
		return new OrganClientFallbackFactory();
	}

	@Bean
	public DepartmentClientFallbackFactory departmentClientFallbackFactory() {
		return new DepartmentClientFallbackFactory();
	}

}
