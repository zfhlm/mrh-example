package org.lushen.mrh.cloud.gateway.config;

import org.lushen.mrh.cloud.gateway.filters.AuthenticateGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.CreateLoginTokenGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.DeployApiGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.ExampleGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.ModifyLoginResponseBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestEnabledGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintRequestLineGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.filters.PrintResponseJsonBodyGatewayFilterFactory;
import org.lushen.mrh.cloud.gateway.supports.DefaultTokenGenerator;
import org.lushen.mrh.cloud.gateway.supports.GatewayApiMacther;
import org.lushen.mrh.cloud.gateway.supports.GatewayRoleMatcher;
import org.lushen.mrh.cloud.gateway.supports.GatewayTokenGenerator;
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
	public ExampleGatewayFilterFactory exampleGatewayFilterFactory() {
		return new ExampleGatewayFilterFactory();
	}

	@Bean
	public PrintRequestEnabledGatewayFilterFactory printRequestEnabledGatewayFilterFactory() {
		return new PrintRequestEnabledGatewayFilterFactory();
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
	public DeployApiGatewayFilterFactory deployApiGatewayFilterFactory(GatewayApiMacther apiMacther) {
		return new DeployApiGatewayFilterFactory(apiMacther);
	}

	@Bean
	public AuthenticateGatewayFilterFactory authenticateGatewayFilterFactory(GatewayRoleMatcher roleMatcher, GatewayTokenGenerator tokenGenerator) {
		return new AuthenticateGatewayFilterFactory(roleMatcher, tokenGenerator);
	}

	@Bean
	public CreateLoginTokenGatewayFilterFactory createLoginTokenGatewayFilterFactory(GatewayTokenGenerator tokenGenerator) {
		return new CreateLoginTokenGatewayFilterFactory(tokenGenerator);
	}

	@Bean
	public ModifyLoginResponseBodyGatewayFilterFactory modifyLoginResponseBodyGatewayFilterFactory(ObjectMapper objectMapper) {
		return new ModifyLoginResponseBodyGatewayFilterFactory(objectMapper);
	}

}
