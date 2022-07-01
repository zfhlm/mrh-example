package org.lushen.mrh.cloud.reference.gateway;

import java.util.List;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 网关权限信息事件
 * 
 * @author hlm
 */
public class GatewayPermissionEvent extends RemoteApplicationEvent {

	private static final long serialVersionUID = 6114742213437288028L;

	private List<GatewayApi> apis;		// 接口信息

	private List<GatewayRole> roles;	// 角色信息

	private long version;				// 版本号

	@SuppressWarnings("unused")
	private GatewayPermissionEvent() {}

	public GatewayPermissionEvent(Object source, String originService, Destination destination) {
		super(source, originService, destination);
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

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
