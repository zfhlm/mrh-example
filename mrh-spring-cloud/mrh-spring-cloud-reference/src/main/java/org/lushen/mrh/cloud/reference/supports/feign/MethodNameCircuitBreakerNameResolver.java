package org.lushen.mrh.cloud.reference.supports.feign;

import java.lang.reflect.Method;

import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.cloud.openfeign.FeignClient;

import com.google.common.base.CaseFormat;

import feign.Target;

/**
 * <br> 熔断 name 解析器，使用 {@link FeignClient#contextId()} 和 方法名，转换：
 * <br> 
 * <br>    organ-client doSomething  -->  organ-client-do-something
 * <br> 
 * <br>	   organClient  doSomething  -->  organ-client-do-something
 * <br>
 * <br>	   OrganClient  doSomething  -->  organ-client-do-something
 * <br>
 * 
 * @author hlm
 */
public class MethodNameCircuitBreakerNameResolver implements CircuitBreakerNameResolver {

	@Override
	public String resolveCircuitBreakerName(String feignClientName, Target<?> target, Method method) {

		StringBuilder builder = new StringBuilder();

		// 客户端名称
		for(char ch : feignClientName.toCharArray()) {
			if(Character.isLetter(ch) || Character.isDigit(ch) || ch == '-') {
				builder.append(ch);
			}
		}

		// 连接符
		if(builder.charAt(builder.length()-1) != '-') {
			builder.append('-');
		}

		// 方法名称
		for(char ch : method.getName().toCharArray()) {
			if(Character.isLetter(ch) || Character.isDigit(ch)) {
				builder.append(ch);
			}
		}

		// 统一转换
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, builder.toString());
		
	}

}
