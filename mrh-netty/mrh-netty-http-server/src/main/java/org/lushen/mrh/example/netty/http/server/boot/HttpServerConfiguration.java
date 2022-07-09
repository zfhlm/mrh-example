package org.lushen.mrh.example.netty.http.server.boot;

import java.util.List;
import java.util.stream.Collectors;

import org.lushen.mrh.example.netty.http.server.netty.HttpBusinessHandler;
import org.lushen.mrh.example.netty.http.server.netty.HttpFilter;
import org.lushen.mrh.example.netty.http.server.netty.HttpServer;
import org.lushen.mrh.example.netty.http.server.netty.HttpServerConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http server configuration
 * 
 * @author hlm
 */
@Configuration
@EnableConfigurationProperties
public class HttpServerConfiguration {

	@Bean
	@ConfigurationProperties(prefix="http.server")
	public HttpServerConfig httpServerConfig() {
		return new HttpServerConfig();
	}

	@Bean
	@ConditionalOnMissingBean(HttpBusinessHandler.class)
	public HttpBusinessHandler httpBusinessHandler() {
		return new HttpBusinessHandler() {};
	}

	@Bean
	public HttpServerBean httpServerBean(@Autowired HttpServerConfig serverConfig, @Autowired ObjectProvider<HttpFilter> filtersProvider, @Autowired HttpBusinessHandler businessHandler) {
		return new HttpServerBean(serverConfig, filtersProvider.stream().collect(Collectors.toList()), businessHandler);
	}

	private class HttpServerBean extends HttpServer implements InitializingBean, DisposableBean {

		public HttpServerBean(HttpServerConfig serverConfig, List<HttpFilter> serverFilters, HttpBusinessHandler businessHandler) {
			super(serverConfig, serverFilters, businessHandler);
		}

		@Override
		public void afterPropertiesSet() throws Exception {
			new Thread(this).start();
		}

		@Override
		public void destroy() throws Exception {
			super.close();
		}

	}

}
