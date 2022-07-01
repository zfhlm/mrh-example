package org.lushen.mrh.cloud.api.admin.config;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.api.admin.web.PermissionController;
import org.lushen.mrh.cloud.reference.gateway.GatewayPermissionEvent;
import org.lushen.mrh.cloud.reference.gateway.GatewayStartedEvent;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.RootClassFilter;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 网关事件发布与监听
 * 
 * @author hlm
 */
@SuppressWarnings("serial")
@Component
public class GatewayBusEventPublisherListener extends DefaultPointcutAdvisor implements ApplicationListener<ApplicationEvent>, InitializingBean {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private BusProperties busProperties;
	@Autowired
	private Destination.Factory destinationFactory;
	@Autowired
	private GatewayPermissionProperties properties;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// 网关启动事件、应用启动事件
		if(event instanceof GatewayStartedEvent || event instanceof ApplicationReadyEvent) {
			publish();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// AOP 拦截权限更新操作
		this.setAdvice(new AfterReturningAdvice() {
			@Override
			public void afterReturning(Object returnValue, Method method, Object[] arg2, Object target) throws Throwable {
				publish();
			}
		});
		this.setPointcut(new Pointcut() {
			@Override
			public ClassFilter getClassFilter() {
				return new RootClassFilter(PermissionController.class);
			}
			@Override
			public MethodMatcher getMethodMatcher() {
				return new StaticMethodMatcher() {
					@Override
					public boolean matches(Method method, Class<?> targetClass) {
						return method.getName().equals("update");
					}
				};
			}
		});
	}

	private void publish() {

		// 发布权限信息事件
		GatewayPermissionEvent event = new GatewayPermissionEvent(this, busProperties.getId(), destinationFactory.getDestination(null));
		event.setApis(properties.getApis());
		event.setRoles(properties.getRoles());
		event.setVersion(properties.getVersion());
		applicationContext.publishEvent(event);
		log.info("publish event " + event);

	}

}
