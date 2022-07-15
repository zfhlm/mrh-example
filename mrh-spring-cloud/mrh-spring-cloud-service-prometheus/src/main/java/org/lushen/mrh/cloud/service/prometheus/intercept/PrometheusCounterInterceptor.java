package org.lushen.mrh.cloud.service.prometheus.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class PrometheusCounterInterceptor implements HandlerInterceptor, WebMvcConfigurer, InitializingBean {

	@Autowired
	private MeterRegistry registry;

	private Counter counter;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.counter = this.registry.counter("http_api_requests_second_count");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this).addPathPatterns("/api/**");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		this.counter.increment();
		return true;
	}

}
