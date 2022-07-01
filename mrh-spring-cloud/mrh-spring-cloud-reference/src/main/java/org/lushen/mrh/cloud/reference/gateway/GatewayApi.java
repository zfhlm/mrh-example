package org.lushen.mrh.cloud.reference.gateway;

import java.io.Serializable;

import org.springframework.http.HttpMethod;

/**
 * 网关接口信息
 * 
 * @author hlm
 */
public class GatewayApi implements Serializable {

	private static final long serialVersionUID = 4577510006064437260L;

	private long id;                        // 接口ID

	private String name;                 	// 接口名称

	private String serviceId;               // 接口所属服务

	private HttpMethod method;           	// 接口请求路径

	private String path;                 	// 接口请求方法

	private boolean isEnabled;              // 是否允许使用

	private boolean isAnonymous;            // 是否允许匿名访问

	private boolean isLogin;                // 是否登录接口

	private boolean isPrintReqLine;			// 是否输出请求 line 日志

	private boolean isPrintReqJson;			// 是否输出请求 json 日志

	private boolean isPrintResJson;			// 是否输出响应 json 日志

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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isAnonymous() {
		return isAnonymous;
	}

	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public boolean isPrintReqLine() {
		return isPrintReqLine;
	}

	public void setPrintReqLine(boolean isPrintReqLine) {
		this.isPrintReqLine = isPrintReqLine;
	}

	public boolean isPrintReqJson() {
		return isPrintReqJson;
	}

	public void setPrintReqJson(boolean isPrintReqJson) {
		this.isPrintReqJson = isPrintReqJson;
	}

	public boolean isPrintResJson() {
		return isPrintResJson;
	}

	public void setPrintResJson(boolean isPrintResJson) {
		this.isPrintResJson = isPrintResJson;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", serviceId=");
		builder.append(serviceId);
		builder.append(", method=");
		builder.append(method);
		builder.append(", path=");
		builder.append(path);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", isAnonymous=");
		builder.append(isAnonymous);
		builder.append(", isLogin=");
		builder.append(isLogin);
		builder.append(", isPrintReqLine=");
		builder.append(isPrintReqLine);
		builder.append(", isPrintReqJson=");
		builder.append(isPrintReqJson);
		builder.append(", isPrintResJson=");
		builder.append(isPrintResJson);
		builder.append("]");
		return builder.toString();
	}

}
