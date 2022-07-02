package org.lushen.mrh.cloud.gateway.config;

import org.lushen.mrh.cloud.gateway.filters.AuthenticateGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.ModifyLoginResponseBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintCircuitBaseOnApiGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintCircuitGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestLineGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintResponseJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.supports.GatewayApiMacther;
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

	@Bean
	@Validated
	@ConfigurationProperties(prefix="spring.cloud.gateway.token")
	public JsonWebTokenGenerator jsonWebTokenGenerator() {
		return new JsonWebTokenGenerator();
	}

	@Bean
	@ConfigurationProperties(prefix="spring.cloud.gateway.token.repository")
	@ConditionalOnProperty(prefix="spring.cloud.gateway.token.repository", name="type", havingValue="redis", matchIfMissing=false)
	public RedisTokenRepository redisTokenRepository(ReactiveStringRedisTemplate redisTemplate) {
		return new RedisTokenRepository(redisTemplate);
	}

	@Bean
	@ConditionalOnMissingBean(GatewayTokenRepository.class)
	public UnrealizedTokenRepository unrealizedTokenRepository() {
		return new UnrealizedTokenRepository();
	}

	@Bean
	public PrintCircuitGatewayFilterFactory printCircuitGatewayFilterFactory() {
		return new PrintCircuitGatewayFilterFactory();
	}

	@Bean
	public PrintRequestLineGatewayFilterFactory printRequestLineGatewayFilterFactory() {
		return new PrintRequestLineGatewayFilterFactory();
	}

	@Bean
	public PrintRequestJsonBodyGatewayFilterFactory printRequestJsonBodyGatewayFilterFactory() {
		return new PrintRequestJsonBodyGatewayFilterFactory();
	}

	@Bean
	public PrintResponseJsonBodyGatewayFilterFactory printResponseJsonBodyGatewayFilterFactory() {
		return new PrintResponseJsonBodyGatewayFilterFactory();
	}

	@Bean
	public PrintCircuitBaseOnApiGatewayFilterFactory deployApiGatewayFilterFactory(GatewayApiMacther apiMacther) {
		return new PrintCircuitBaseOnApiGatewayFilterFactory(apiMacther);
	}

	@Bean
	public AuthenticateGatewayFilterFactory authenticateGatewayFilterFactory(
			GatewayApiMacther apiMacther, 
			GatewayRoleMatcher roleMatcher,
			GatewayTokenGenerator tokenGenerator, 
			GatewayTokenRepository tokenRepository) {
		return new AuthenticateGatewayFilterFactory(apiMacther, roleMatcher, tokenGenerator, tokenRepository);
	}

	@Bean
	public ModifyLoginResponseBodyGatewayFilterFactory modifyLoginResponseBodyGatewayFilterFactory(ObjectMapper objectMapper) {
		return new ModifyLoginResponseBodyGatewayFilterFactory(objectMapper);
	}

}
