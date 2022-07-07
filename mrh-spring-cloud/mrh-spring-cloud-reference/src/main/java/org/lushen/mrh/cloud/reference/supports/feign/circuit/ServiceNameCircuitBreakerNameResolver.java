package org.lushen.mrh.cloud.reference.supports.feign.circuit;

import java.lang.reflect.Method;

import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.cloud.openfeign.FeignClient;

import feign.Target;

/**
 * 熔断 name 解析器，使用 {@link FeignClient#name()} 值，即接口对应的 服务名
 * 
 * @author hlm
 */
public class ServiceNameCircuitBreakerNameResolver implements CircuitBreakerNameResolver {

	@Override
	public String resolveCircuitBreakerName(String feignClientName, Target<?> target, Method method) {
		return target.name();
	}

}