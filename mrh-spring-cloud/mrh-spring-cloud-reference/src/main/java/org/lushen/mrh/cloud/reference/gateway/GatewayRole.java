package org.lushen.mrh.cloud.reference.gateway;

import java.io.Serializable;
import java.util.Set;

/**
 * 网关角色信息
 * 
 * @author hlm
 */
public class GatewayRole implements Serializable {

	private static final long serialVersionUID = -8365187605424523995L;

	private long id;                        	// 角色ID

	private String name;                    	// 角色名称

	private boolean isEnabled;                  // 是否允许使用

	private Set<Long> apis;                   	// 角色可访问 api 列表

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

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Set<Long> getApis() {
		return apis;
	}

	public void setApis(Set<Long> apis) {
		this.apis = apis;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", apis=");
		builder.append(apis);
		builder.append("]");
		return builder.toString();
	}

}
