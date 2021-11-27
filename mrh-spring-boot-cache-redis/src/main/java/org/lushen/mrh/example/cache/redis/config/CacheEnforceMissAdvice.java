package org.lushen.mrh.example.cache.redis.config;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

/**
 * 缓存强制 miss 切面
 * 
 * @author hlm
 */
@SuppressWarnings("serial")
public class CacheEnforceMissAdvice extends DefaultPointcutAdvisor implements InitializingBean, BeanPostProcessor, PriorityOrdered {

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	/**
	 * 重写缓存切面，上下文强制 miss
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		// @link org.springframework.cache.annotation.ProxyCachingConfiguration
		if(bean instanceof BeanFactoryCacheOperationSourceAdvisor) {
			BeanFactoryCacheOperationSourceAdvisor advisor = (BeanFactoryCacheOperationSourceAdvisor)bean;
			MethodInterceptor advice = (MethodInterceptor)advisor.getAdvice();
			advisor.setAdvice(new MethodInterceptor() {
				@Override
				public Object invoke(MethodInvocation invocation) throws Throwable {
					if(CacheEnforceManager.getInstance().isMiss()) {
						return invocation.proceed();
					} else {
						return advice.invoke(invocation);
					}
				}
			});
		}

		return bean;
	}

	/**
	 * 配置缓存 miss 上下文切面，使用注解 @CacheEnforceMiss 强制 miss
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

		this.setOrder(Ordered.HIGHEST_PRECEDENCE);

		this.setPointcut(new Pointcut() {
			@Override
			public MethodMatcher getMethodMatcher() {
				return new AnnotationMethodMatcher(CacheEnforceMiss.class);
			}
			@Override
			public ClassFilter getClassFilter() {
				return ClassFilter.TRUE;
			};
		});

		this.setAdvice(new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				CacheEnforceMiss annotation = invocation.getMethod().getAnnotation(CacheEnforceMiss.class);
				if(annotation != null) {
					String id = CacheEnforceManager.getInstance().configure(annotation.value(), annotation.transfer());
					try {
						return invocation.proceed();
					} finally {
						CacheEnforceManager.getInstance().release(id);
					}
				} else {
					return invocation.proceed();
				}
			}
		});

	}

}
