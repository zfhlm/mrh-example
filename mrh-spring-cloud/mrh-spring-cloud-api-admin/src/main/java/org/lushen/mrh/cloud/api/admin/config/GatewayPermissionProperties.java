package org.lushen.mrh.cloud.api.admin.config;

import java.util.List;

import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.lushen.mrh.cloud.reference.gateway.GatewayRole;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 注入测试权限配置
 * 
 * @author hlm
 */
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix="gateway")
public class GatewayPermissionProperties {

	private long version;

	private List<GatewayApi> apis;

	private List<GatewayRole> roles;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<GatewayApi> getApis() {
		return apis;
	}

	public void setApis(List<GatewayApi> apis) {
		this.apis = apis;
	}

	public List<GatewayRole> getRoles() {
		return roles;
	}

	public void setRoles(List<GatewayRole> roles) {
		this.roles = roles;
	}

}
