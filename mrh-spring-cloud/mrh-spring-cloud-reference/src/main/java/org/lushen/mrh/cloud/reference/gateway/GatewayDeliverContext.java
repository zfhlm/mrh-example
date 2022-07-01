package org.lushen.mrh.cloud.reference.gateway;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * 网关 登录信息 上下文
 * 
 * @author hlm
 */
public abstract class GatewayDeliverContext {

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public abstract long id();

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
	protected abstract String name();

	/**
	 * 获取角色ID
	 * 
	 * @return
	 */
	public abstract long roleId();

	/**
	 * 获取来源
	 * 
	 * @return
	 */
	public abstract int source();

	/**
	 * 创建默认实现
	 * 
	 * @param attrAccessor
	 * @return
	 */
	public static final GatewayDeliverContext create(Function<String, Object> attrAccessor) {

		return new GatewayDeliverContext() {

			@Override
			public long id() {
				Object id = attrAccessor.apply(GatewayDeliverHeaders.JWT_DELIVER_ID_HEADER);
				if(id instanceof Number) {
					return ((Number) id).longValue();
				} else if(id instanceof String) {
					return Long.parseLong((String)id);
				} else {
					return Long.parseLong(String.valueOf(id));
				}
			}

			@Override
			public String name() {
				Object name = attrAccessor.apply(GatewayDeliverHeaders.JWT_DELIVER_NAME_HEADER);
				if(name instanceof String) {
					return (String) name;
				} else {
					return String.valueOf(name);
				}
			}

			@Override
			public long roleId() {
				Object roleId = attrAccessor.apply(GatewayDeliverHeaders.JWT_DELIVER_ROLE_ID_HEADER);
				if(roleId instanceof Number) {
					return ((Number) roleId).longValue();
				} else if(roleId instanceof String) {
					return Long.parseLong((String)roleId);
				} else {
					return Long.parseLong(String.valueOf(roleId));
				}
			}

			@Override
			public int source() {
				Object source = attrAccessor.apply(GatewayDeliverHeaders.JWT_DELIVER_SOURCE_HEADER);
				if(source instanceof Number) {
					return ((Number)source).intValue();
				} else if(source instanceof String) {
					return Integer.parseInt((String)source);
				} else {
					return Integer.parseInt(String.valueOf(source));
				}
			}

		};

	}

}
