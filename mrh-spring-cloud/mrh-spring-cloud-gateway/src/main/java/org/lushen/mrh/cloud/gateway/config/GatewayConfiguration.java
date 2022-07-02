package org.lushen.mrh.cloud.gateway.config;

import org.lushen.mrh.cloud.gateway.filters.AuthenticateGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.ModifyLoginResponseBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.NoRouteGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintCircuitBaseOnApiGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintCircuitGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestLineGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintResponseJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.RequestRateLimiterGatewayFilterFactoryAdapter;
import org.lushen.mrh.cloud.gateway.supports.GatewayApiMacther;
import org.lushen.mrh.cloud.gateway.supports.GatewayRateLimiterKeyResolver;
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
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * gateway bean 注入配置
 * 
 * @author hlm
 */
@Configuration
@EnableConfigurationProperties
public class GatewayConfiguration {

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

	// 限流 key 处理器
	@Bean
	public GatewayRateLimiterKeyResolver gatewayRateLimiterKeyResolver() {
		return new GatewayRateLimiterKeyResolver();
	}

	// 限流过滤器适配工厂
	@Bean
	public RequestRateLimiterGatewayFilterFactoryAdapter requestRateLimiterGatewayFilterFactoryAdapter(RateLimiter<?> rateLimiter, KeyResolver resolver) {
		return new RequestRateLimiterGatewayFilterFactoryAdapter(rateLimiter, resolver);
	}

}
