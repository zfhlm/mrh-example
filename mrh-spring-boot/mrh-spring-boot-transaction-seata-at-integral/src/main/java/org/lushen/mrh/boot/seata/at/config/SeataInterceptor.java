package org.lushen.mrh.boot.seata.at.config;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.seata.core.context.RootContext;

/**
 * seata XID 传递拦截器，远程调用测试使用
 * 
 * @author hlm
 */
@Component
public class SeataInterceptor implements HandlerInterceptor, WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this).addPathPatterns("/**");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Optional.ofNullable(request.getHeader(RootContext.KEY_XID)).ifPresent(value -> {
			RootContext.bind(value);
		});
		return true;
	}

}
