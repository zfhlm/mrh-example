package org.lushen.mrh.cloud.reference.supports.feign;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.cloud.openfeign.FeignClient;

import com.google.common.base.CaseFormat;

import feign.Target;

/**
 * <br> 熔断 name 解析器，使用 {@link FeignClient#contextId()}，去除非字母、数值或横线，再经过转换：
 * <br> 
 * <br>    organ-client --> organ-client
 * <br> 
 * <br>	   organClient  --> organ-client
 * <br>
 * <br>	   OrganClient  --> organ-client
 * <br>
 * 
 * @author hlm
 */
public class ClientNameCircuitBreakerNameResolver implements CircuitBreakerNameResolver {

	private final Map<String, String> mappings = new ConcurrentHashMap<>();

	@Override
	public String resolveCircuitBreakerName(String feignClientName, Target<?> target, Method method) {
		return mappings.computeIfAbsent(feignClientName, k -> {
			return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, feignClientName.replaceAll("[^a-zA-Z0-9\\-]", ""));
		});
	}

}
