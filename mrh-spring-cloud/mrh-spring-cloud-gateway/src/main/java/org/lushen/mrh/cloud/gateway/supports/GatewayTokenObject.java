package org.lushen.mrh.cloud.gateway.supports;

import java.time.Duration;

/**
 * 网关 登录信息 上下文
 * 
 * @author hlm
 */
public class GatewayTokenObject {

	private long id;

	private String name;

	private long roleId;

	private int source;

	private Duration expired;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public Duration getExpired() {
		return expired;
	}

	public void setExpired(Duration expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", source=");
		builder.append(source);
		builder.append(", expired=");
		builder.append(expired);
		builder.append("]");
		return builder.toString();
	}

}
