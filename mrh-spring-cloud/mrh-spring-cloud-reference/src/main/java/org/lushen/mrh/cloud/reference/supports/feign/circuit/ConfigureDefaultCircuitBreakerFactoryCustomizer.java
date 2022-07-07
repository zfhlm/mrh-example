package org.lushen.mrh.cloud.reference.supports.feign.circuit;

import java.util.Collections;
import java.util.Optional;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import io.github.resilience4j.common.CompositeCustomizer;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.autoconfigure.TimeLimiterProperties;

/**
 * 读取 resilience4j.circuitbreaker.configs.default 和 resilience4j.timelimiter.configs.default 作为熔断、限时默认配置
 * 
 * @author hlm
 */
public class ConfigureDefaultCircuitBreakerFactoryCustomizer implements Customizer<Resilience4JCircuitBreakerFactory> {

	private static final String name = "default";	// 默认配置名称

	private CircuitBreakerProperties circuitBreakerProperties;

	private TimeLimiterProperties timeLimiterProperties;

	public ConfigureDefaultCircuitBreakerFactoryCustomizer(
			CircuitBreakerProperties circuitBreakerProperties,
			TimeLimiterProperties timeLimiterProperties) {
		super();
		this.circuitBreakerProperties = circuitBreakerProperties;
		this.timeLimiterProperties = timeLimiterProperties;
	}

	@Override
	public void customize(Resilience4JCircuitBreakerFactory circuitBreakerFactory) {

		// 读取熔断默认配置
		CircuitBreakerConfig defaultCircuitBreakerConfig = Optional.ofNullable(this.circuitBreakerProperties.getConfigs().get(name))
				.map(properties -> this.circuitBreakerProperties.createCircuitBreakerConfig(name, properties, new CompositeCustomizer<>(Collections.emptyList())))
				.orElse(CircuitBreakerConfig.ofDefaults());

		// 读取限时默认配置
		TimeLimiterConfig defaultTimeLimiterConfig = Optional.ofNullable(this.timeLimiterProperties.getConfigs().get(name))
				.map(properties -> this.timeLimiterProperties.createTimeLimiterConfig(name, properties, new CompositeCustomizer<>(Collections.emptyList())))
				.orElse(TimeLimiterConfig.ofDefaults());

		// 配置到断路器工厂
		circuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(defaultCircuitBreakerConfig)
				.timeLimiterConfig(defaultTimeLimiterConfig)
				.build());
	}

}
