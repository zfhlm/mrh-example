package org.lushen.mrh.cloud.service.prometheus.intercept;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@Component
public class PrometheusHistogramInterceptor implements HandlerInterceptor, WebMvcConfigurer, InitializingBean {

	@Autowired
	private PrometheusMeterRegistry registry;

	private DistributionSummary summary;

	@Override
	public void afterPropertiesSet() throws Exception {
		summary = registry.summary("http_api_requests_bytes");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this).addPathPatterns("/api/**");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		summary.record(Optional.ofNullable(request.getContentLength()).filter(e -> e>0).orElse(0));
		return true;
	}

}
