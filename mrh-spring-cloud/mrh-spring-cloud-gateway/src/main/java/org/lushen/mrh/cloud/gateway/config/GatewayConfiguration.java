package org.lushen.mrh.cloud.gateway.config;

import org.lushen.mrh.cloud.gateway.filters.AuthenticateGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.CorsWebFilter;
import org.lushen.mrh.cloud.gateway.filters.GuavaRateLimiterGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.ModifyLoginResponseBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.NoRouteGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintCircuitBaseOnApiGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintCircuitGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestLineGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintResponseJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.SentinelGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.predicates.PathStartWithRoutePredicateFactory;
import org.lushen.mrh.cloud.gateway.supports.GatewayApiMacther;
import org.lushen.mrh.cloud.gateway.supports.GatewayExchangeUtils;
import org.lushen.mrh.cloud.gateway.supports.GatewayRoleMatcher;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator.JsonWebTokenGenerator;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenRepository;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenRepository.RedisTokenRepository;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenRepository.UnrealizedTokenRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.WebFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * gateway bean 注入配置
 * 
 * @author hlm
 */
@Configuration
@EnableConfigurationProperties
public class GatewayConfiguration {

	// 注册路由断路工厂
	@Bean
	public PathStartWithRoutePredicateFactory pathStartWithRoutePredicateFactory() {
		return new PathStartWithRoutePredicateFactory();
	}

	// 登录令牌生成器
	@Bean
	@Validated
	@ConfigurationProperties(prefix="spring.cloud.gateway.token")
	public JsonWebTokenGenerator jsonWebTokenGenerator() {
		return new JsonWebTokenGenerator();
	}

	// 登录令牌redis存储实现
	@Bean
	@ConfigurationProperties(prefix="spring.cloud.gateway.token.repository")
	@ConditionalOnProperty(prefix="spring.cloud.gateway.token.repository", name="type", havingValue="redis", matchIfMissing=false)
	public RedisTokenRepository redisTokenRepository(ReactiveStringRedisTemplate redisTemplate) {
		return new RedisTokenRepository(redisTemplate);
	}

	// 登录令牌无存储实现
	@Bean
	@ConditionalOnMissingBean(GatewayTokenRepository.class)
	public UnrealizedTokenRepository unrealizedTokenRepository() {
		return new UnrealizedTokenRepository();
	}

	// 日志输出开关状态过滤器工厂
	@Bean
	public PrintCircuitGatewayFilterFactory printCircuitGatewayFilterFactory() {
		return new PrintCircuitGatewayFilterFactory();
	}

	// 日志输出开关状态过滤器工厂(根据 GatewayApi 配置)
	@Bean
	public PrintCircuitBaseOnApiGatewayFilterFactory deployApiGatewayFilterFactory(GatewayApiMacther apiMacther) {
		return new PrintCircuitBaseOnApiGatewayFilterFactory(apiMacther);
	}

	// 日志输出请求 line 过滤器工厂
	@Bean
	public PrintRequestLineGatewayFilterFactory printRequestLineGatewayFilterFactory() {
		return new PrintRequestLineGatewayFilterFactory();
	}

	// 日志输出请求 Json body 过滤器工厂
	@Bean
	public PrintRequestJsonBodyGatewayFilterFactory printRequestJsonBodyGatewayFilterFactory() {
		return new PrintRequestJsonBodyGatewayFilterFactory();
	}

	// 日志输出响应 Json body 过滤器工厂
	@Bean
	public PrintResponseJsonBodyGatewayFilterFactory printResponseJsonBodyGatewayFilterFactory() {
		return new PrintResponseJsonBodyGatewayFilterFactory();
	}

	// 登录令牌、登录验证、权限验证 过滤器工厂
	@Bean
	public AuthenticateGatewayFilterFactory authenticateGatewayFilterFactory(
			GatewayApiMacther apiMacther, 
			GatewayRoleMatcher roleMatcher,
			GatewayTokenGenerator tokenGenerator, 
			GatewayTokenRepository tokenRepository) {
		return new AuthenticateGatewayFilterFactory(apiMacther, roleMatcher, tokenGenerator, tokenRepository);
	}

	// 登录令牌配置到 Json body 过滤器工厂
	@Bean
	public ModifyLoginResponseBodyGatewayFilterFactory modifyLoginResponseBodyGatewayFilterFactory(ObjectMapper objectMapper) {
		return new ModifyLoginResponseBodyGatewayFilterFactory(objectMapper);
	}

	// 无路由过滤器工厂
	@Bean
	public NoRouteGatewayFilterFactory noRouteGatewayFilterFactory() {
		return new NoRouteGatewayFilterFactory();
	}

	// sentinel 限流过滤器工厂
	@Bean
	@ConfigurationProperties(prefix="sentinel.gateway.flow")
	public SentinelGatewayFilterFactory SentinelConfiguration() {
		return new SentinelGatewayFilterFactory();
	}

	// guava 限流过滤器工厂
	@Bean
	public GuavaRateLimiterGatewayFilterFactory guavaRateLimiterGatewayFilterFactory() {
		return new GuavaRateLimiterGatewayFilterFactory();
	}

	// 允许跨域过滤器
	@Bean
	public WebFilter corsFilter() {
		return new CorsWebFilter();
	}

	// 初始化 sleuth bean holder
	@Bean
	public ApplicationContextAware sleuthTracerInitializer() {
		return context -> GatewayExchangeUtils.Sleuth.initialize(context);
	}

}
