package org.lushen.mrh.cloud.gateway.filters;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;

/**
 * 适配 sentinel global filter 转换为 gateway filter
 * 
 * @author hlm
 */
public class SentinelGatewayFilterFactory extends AbstractGatewayFilterFactory<NameConfig> implements InitializingBean {

	private Set<GatewayFlowRule> rules = new HashSet<GatewayFlowRule>();

	public SentinelGatewayFilterFactory() {
		super(NameConfig.class);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		GatewayRuleManager.loadRules(this.rules);
	}

	@Override
	public GatewayFilter apply(NameConfig config) {
		return (exchange, chain) -> new SentinelGatewayFilter().filter(exchange, chain);
	}

	public Set<GatewayFlowRule> getRules() {
		return rules;
	}

	public void setRules(Set<GatewayFlowRule> rules) {
		this.rules = rules;
	}

}
