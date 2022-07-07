package org.lushen.mrh.cloud.reference.supports.feign.circuit;

import java.util.Collections;
import java.util.Optional;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadConfigurationBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadProvider;
import org.springframework.cloud.client.circuitbreaker.Customizer;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.autoconfigure.BulkheadProperties;
import io.github.resilience4j.bulkhead.autoconfigure.ThreadPoolBulkheadProperties;
import io.github.resilience4j.common.CompositeCustomizer;

/**
 * 读取 resilience4j.thread-pool-bulkhead.configs.default 和 resilience4j.bulkhead.configs.default 作为容错隔离默认配置
 * 
 * @author hlm
 */
public class ConfigureDefaultBulkheadProviderCustomizer implements Customizer<Resilience4jBulkheadProvider> {

	private static final String name = "default";	// 默认配置名称

	private ThreadPoolBulkheadProperties threadPoolBulkheadProperties;

	private BulkheadProperties bulkheadProperties;

	public ConfigureDefaultBulkheadProviderCustomizer(
			ThreadPoolBulkheadProperties threadPoolBulkheadProperties,
			BulkheadProperties bulkheadProperties) {
		super();
		this.threadPoolBulkheadProperties = threadPoolBulkheadProperties;
		this.bulkheadProperties = bulkheadProperties;
	}

	@Override
	public void customize(Resilience4jBulkheadProvider provider) {

		// 线程池隔离默认配置
		ThreadPoolBulkheadConfig defaultThreadPoolBulkheadConfig = Optional.ofNullable(this.threadPoolBulkheadProperties.getConfigs().get(name))
				.map(properties -> this.threadPoolBulkheadProperties.createThreadPoolBulkheadConfig(name, new CompositeCustomizer<>(Collections.emptyList())))
				.orElse(ThreadPoolBulkheadConfig.ofDefaults());

		// 信号量隔离默认配置
		BulkheadConfig defaultBulkheadConfig = Optional.ofNullable(this.bulkheadProperties.getConfigs().get(name))
				.map(properties -> this.bulkheadProperties.createBulkheadConfig(properties, new CompositeCustomizer<>(Collections.emptyList()), name))
				.orElse(BulkheadConfig.ofDefaults());

		// 添加到默认配置
		provider.configureDefault(id -> new Resilience4jBulkheadConfigurationBuilder()
				.bulkheadConfig(defaultBulkheadConfig)
				.threadPoolBulkheadConfig(defaultThreadPoolBulkheadConfig)
				.build());

	}

}
