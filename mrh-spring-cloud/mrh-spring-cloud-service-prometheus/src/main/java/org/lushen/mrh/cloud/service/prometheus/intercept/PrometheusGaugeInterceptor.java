package org.lushen.mrh.cloud.service.prometheus.intercept;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.micrometer.core.instrument.MeterRegistry;

@Component
public class PrometheusGaugeInterceptor implements HandlerInterceptor, WebMvcConfigurer, InitializingBean {

	@Autowired
	private MeterRegistry registry;

	private AtomicLong value;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.value = this.registry.gauge("http_api_requests_current_queries_count", new AtomicLong());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this).addPathPatterns("/api/**");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		value.incrementAndGet();
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		value.decrementAndGet();
	}

}
