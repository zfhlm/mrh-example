package org.lushen.mrh.cloud.gateway.config;

import org.lushen.mrh.cloud.gateway.filters.AuthenticateGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.CreateLoginTokenGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.DeployApiGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.ExampleGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.ModifyLoginResponseBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestLineGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintResponseJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.supports.DefaultTokenGenerator;
import org.lushen.mrh.cloud.gateway.supports.DefaultTokenRepository;
import org.lushen.mrh.cloud.gateway.supports.GatewayApiRepository;
import org.lushen.mrh.cloud.gateway.supports.GatewayRoleRepository;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	public GatewayTokenGenerator gatewayTokenGenerator() {
		return new DefaultTokenGenerator();
	}

	@Bean
	public GatewayTokenRepository gatewayTokenRepository() {
		return new DefaultTokenRepository();
	}

	@Bean
	public ExampleGatewayFilterFactory exampleGatewayFilterFactory() {
		return new ExampleGatewayFilterFactory();
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
	public DeployApiGatewayFilterFactory deployApiGatewayFilterFactory(GatewayApiRepository apiRepository) {
		return new DeployApiGatewayFilterFactory(apiRepository);
	}

	@Bean
	public AuthenticateGatewayFilterFactory authenticateGatewayFilterFactory(
			GatewayRoleRepository roleRepository,
			GatewayTokenGenerator tokenGenerator,
			GatewayTokenRepository tokenRepository) {
		return new AuthenticateGatewayFilterFactory(roleRepository, tokenGenerator, tokenRepository);
	}

	@Bean
	public CreateLoginTokenGatewayFilterFactory createLoginTokenGatewayFilterFactory(
			GatewayTokenGenerator tokenGenerator,
			GatewayTokenRepository tokenRepository) {
		return new CreateLoginTokenGatewayFilterFactory(tokenGenerator, tokenRepository);
	}

	@Bean
	public ModifyLoginResponseBodyGatewayFilterFactory modifyLoginResponseBodyGatewayFilterFactory(ObjectMapper objectMapper) {
		return new ModifyLoginResponseBodyGatewayFilterFactory(objectMapper);
	}

}
