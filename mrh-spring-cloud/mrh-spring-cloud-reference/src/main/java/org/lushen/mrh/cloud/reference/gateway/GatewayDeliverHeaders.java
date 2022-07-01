package org.lushen.mrh.cloud.reference.gateway;

/**
 * 网关 登录信息 请求响应头
 * 
 * @author hlm
 */
public class GatewayDeliverHeaders {

	/**
	 * JWT token 请求响应头
	 */
	public static final String JWT_TOKEN_HEADER = "Jwt-Authorization";

	/**
	 * JWT 用户ID 请求响应头
	 */
	public static final String JWT_DELIVER_ID_HEADER = "Jwt-Deliver-Id";

	/**
	 * JWT 用户名称 请求响应头
	 */
	public static final String JWT_DELIVER_NAME_HEADER = "Jwt-Deliver-Name";

	/**
	 * JWT 用户角色ID 请求响应头
	 */
	public static final String JWT_DELIVER_ROLE_ID_HEADER = "Jwt-Deliver-Role-Id";

	/**
	 * JWT 用户登录来源 请求响应头
	 */
	public static final String JWT_DELIVER_SOURCE_HEADER = "Jwt-Deliver-Source";

}
