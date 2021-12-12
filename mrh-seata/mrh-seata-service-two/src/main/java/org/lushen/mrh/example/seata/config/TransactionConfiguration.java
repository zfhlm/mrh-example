package org.lushen.mrh.example.seata.config;

import java.util.Collections;
import java.util.List;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 声明式事务配置
 * 
 * @author hlm
 */
@Configuration
public class TransactionConfiguration {

	@Bean
	public TransactionInterceptor txAdvice(TransactionManager txManager){
		List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Throwable.class)); 
		TransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
		MatchAlwaysTransactionAttributeSource transactionAttributeSource = new MatchAlwaysTransactionAttributeSource();
		transactionAttributeSource.setTransactionAttribute(transactionAttribute);
		return new TransactionInterceptor(txManager, transactionAttributeSource) ;
	}

	@Bean
	public PointcutAdvisor txPointcutAdvisor(TransactionInterceptor txAdvice){
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setAdvice(txAdvice);
		advisor.setPointcut(new Pointcut() {
			@Override
			public MethodMatcher getMethodMatcher() {
				return new AnnotationMethodMatcher(Transactional.class);
			}
			@Override
			public ClassFilter getClassFilter() {
				return ClassFilter.TRUE;
			}
		});
		return advisor;
	}

}
