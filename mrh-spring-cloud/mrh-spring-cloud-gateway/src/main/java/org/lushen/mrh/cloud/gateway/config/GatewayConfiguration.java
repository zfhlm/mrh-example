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
 * gateway bean ????????????
 * 
 * @author hlm
 */
@Configuration
@EnableConfigurationProperties
public class GatewayConfiguration {

	// ????????????????????????
	@Bean
	public PathStartWithRoutePredicateFactory pathStartWithRoutePredicateFactory() {
		return new PathStartWithRoutePredicateFactory();
	}

	// ?????????????????????
	@Bean
	@Validated
	@ConfigurationProperties(prefix="spring.cloud.gateway.token")
	public JsonWebTokenGenerator jsonWebTokenGenerator() {
		return new JsonWebTokenGenerator();
	}

	// ????????????redis????????????
	@Bean
	@ConfigurationProperties(prefix="spring.cloud.gateway.token.repository")
	@ConditionalOnProperty(prefix="spring.cloud.gateway.token.repository", name="type", havingValue="redis", matchIfMissing=false)
	public RedisTokenRepository redisTokenRepository(ReactiveStringRedisTemplate redisTemplate) {
		return new RedisTokenRepository(redisTemplate);
	}

	// ???????????????????????????
	@Bean
	@ConditionalOnMissingBean(GatewayTokenRepository.class)
	public UnrealizedTokenRepository unrealizedTokenRepository() {
		return new UnrealizedTokenRepository();
	}

	// ???????????????????????????????????????
	@Bean
	public PrintCircuitGatewayFilterFactory printCircuitGatewayFilterFactory() {
		return new PrintCircuitGatewayFilterFactory();
	}

	// ???????????????????????????????????????(?????? GatewayApi ??????)
	@Bean
	public PrintCircuitBaseOnApiGatewayFilterFactory deployApiGatewayFilterFactory(GatewayApiMacther apiMacther) {
		return new PrintCircuitBaseOnApiGatewayFilterFactory(apiMacther);
	}

	// ?????????????????? line ???????????????
	@Bean
	public PrintRequestLineGatewayFilterFactory printRequestLineGatewayFilterFactory() {
		return new PrintRequestLineGatewayFilterFactory();
	}

	// ?????????????????? Json body ???????????????
	@Bean
	public PrintRequestJsonBodyGatewayFilterFactory printRequestJsonBodyGatewayFilterFactory() {
		return new PrintRequestJsonBodyGatewayFilterFactory();
	}

	// ?????????????????? Json body ???????????????
	@Bean
	public PrintResponseJsonBodyGatewayFilterFactory printResponseJsonBodyGatewayFilterFactory() {
		return new PrintResponseJsonBodyGatewayFilterFactory();
	}

	// ?????????????????????????????????????????? ???????????????
	@Bean
	public AuthenticateGatewayFilterFactory authenticateGatewayFilterFactory(
			GatewayApiMacther apiMacther, 
			GatewayRoleMatcher roleMatcher,
			GatewayTokenGenerator tokenGenerator, 
			GatewayTokenRepository tokenRepository) {
		return new AuthenticateGatewayFilterFactory(apiMacther, roleMatcher, tokenGenerator, tokenRepository);
	}

	// ????????????????????? Json body ???????????????
	@Bean
	public ModifyLoginResponseBodyGatewayFilterFactory modifyLoginResponseBodyGatewayFilterFactory(ObjectMapper objectMapper) {
		return new ModifyLoginResponseBodyGatewayFilterFactory(objectMapper);
	}

	// ????????????????????????
	@Bean
	public NoRouteGatewayFilterFactory noRouteGatewayFilterFactory() {
		return new NoRouteGatewayFilterFactory();
	}

	// sentinel ?????????????????????
	@Bean
	@ConfigurationProperties(prefix="sentinel.gateway.flow")
	public SentinelGatewayFilterFactory SentinelConfiguration() {
		return new SentinelGatewayFilterFactory();
	}

	// guava ?????????????????????
	@Bean
	public GuavaRateLimiterGatewayFilterFactory guavaRateLimiterGatewayFilterFactory() {
		return new GuavaRateLimiterGatewayFilterFactory();
	}

	// ?????????????????????
	@Bean
	public WebFilter corsFilter() {
		return new CorsWebFilter();
	}

	// ????????? sleuth bean holder
	@Bean
	public ApplicationContextAware sleuthTracerInitializer() {
		return context -> GatewayExchangeUtils.Sleuth.initialize(context);
	}

}
