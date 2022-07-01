package org.lushen.mrh.cloud.gateway.supports;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 网关 登录信息 上下文
 * 
 * @author hlm
 */
public class GatewayTokenContext {

	private Object id;

	private Object name;

	private Object roleId;

	private Object source;

	public GatewayTokenContext(Object id, Object name, Object roleId, Object source) {
		super();
		this.id = id;
		this.name = name;
		this.roleId = roleId;
		this.source = source;
	}

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public long id() {
		if(id instanceof Number) {
			return ((Number) id).longValue();
		} else if(id instanceof String) {
			return Long.parseLong((String)id);
		} else {
			return Long.parseLong(String.valueOf(id));
		}
	}

	/**
	 * 获取 URL 编码 用户名称
	 * 
	 * @return
	 */
	public String encodedName() {
		try {
			String name = name();
			return name == null? null : URLEncoder.encode(name, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取 URL 解码 用户名称
	 * 
	 * @return
	 */
	public String decodedName() {
		try {
			String name = name();
			return name == null? null : URLDecoder.decode(name, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取用户名称
	 * 
	 * @return
	 */
	protected String name() {
		if(name instanceof String) {
			return (String) name;
		} else {
			return String.valueOf(name);
		}
	}

	/**
	 * 获取角色ID
	 * 
	 * @return
	 */
	public long roleId() {
		if(roleId instanceof Number) {
			return ((Number) roleId).longValue();
		} else if(roleId instanceof String) {
			return Long.parseLong((String)roleId);
		} else {
			return Long.parseLong(String.valueOf(roleId));
		}
	}

	/**
	 * 获取来源
	 * 
	 * @return
	 */
	public int source() {
		if(source instanceof Number) {
			return ((Number)source).intValue();
		} else if(source instanceof String) {
			return Integer.parseInt((String)source);
		} else {
			return Integer.parseInt(String.valueOf(source));
		}
	}

}
