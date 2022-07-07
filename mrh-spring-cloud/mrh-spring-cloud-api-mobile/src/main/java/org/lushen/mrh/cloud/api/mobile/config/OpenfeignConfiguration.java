package org.lushen.mrh.cloud.api.mobile.config;

import org.lushen.mrh.cloud.reference.fallback.DepartmentClientFallbackFactory;
import org.lushen.mrh.cloud.reference.fallback.OrganClientFallbackFactory;
import org.lushen.mrh.cloud.reference.supports.feign.circuit.ClientNameCircuitBreakerNameResolver;
import org.lushen.mrh.cloud.reference.supports.feign.circuit.ConfigureDefaultBulkheadProviderCustomizer;
import org.lushen.mrh.cloud.reference.supports.feign.circuit.ConfigureDefaultCircuitBreakerFactoryCustomizer;
import org.lushen.mrh.cloud.reference.supports.feign.decoder.InnerServerErrorBodyDecoderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.bulkhead.autoconfigure.BulkheadProperties;
import io.github.resilience4j.bulkhead.autoconfigure.ThreadPoolBulkheadProperties;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import io.github.resilience4j.timelimiter.autoconfigure.TimeLimiterProperties;

/**
 * openfeign 配置
 * 
 * @author hlm
 */
@Configuration
public class OpenfeignConfiguration {

	// 异常解码器
	@Bean
	public InnerServerErrorBodyDecoderFactory feignInnerServerErrorBodyDecoderFactory() {
		return new InnerServerErrorBodyDecoderFactory();
	}

	// 注入熔断和限时默认配置
	@Bean
	public ConfigureDefaultCircuitBreakerFactoryCustomizer configureDefaultCircuitBreakerFactoryCustomizer(
			CircuitBreakerProperties circuitBreakerProperties,
			TimeLimiterProperties timeLimiterProperties) {
		return new ConfigureDefaultCircuitBreakerFactoryCustomizer(circuitBreakerProperties, timeLimiterProperties);
	}

	// 注入容错隔离配置
	@Bean
	public ConfigureDefaultBulkheadProviderCustomizer configureDefaultBulkheadProviderCustomizer(
			ThreadPoolBulkheadProperties threadPoolBulkheadProperties,
			BulkheadProperties bulkheadProperties) {
		return new ConfigureDefaultBulkheadProviderCustomizer(threadPoolBulkheadProperties, bulkheadProperties);
	}

	// 熔断实例名称解析器
	@Bean
	public ClientNameCircuitBreakerNameResolver clientNameCircuitBreakerNameResolver() {
		return new ClientNameCircuitBreakerNameResolver();
	}

	// fallback factory
	@Bean
	public OrganClientFallbackFactory organClientFallbackFactory() {
		return new OrganClientFallbackFactory();
	}

	// fallback factory
	@Bean
	public DepartmentClientFallbackFactory departmentClientFallbackFactory() {
		return new DepartmentClientFallbackFactory();
	}

}
