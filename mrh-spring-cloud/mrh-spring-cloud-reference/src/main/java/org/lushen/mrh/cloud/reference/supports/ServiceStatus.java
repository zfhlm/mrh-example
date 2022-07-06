package org.lushen.mrh.cloud.reference.supports;

import java.io.Serializable;

/**
 * 状态码定义
 * 
 * @author hlm
 */
public final class ServiceStatus implements Serializable {

	// 成功状态码

	public static final ServiceStatus OK = new ServiceStatus(200, "成功!");

	// http 4xx 状态码

	public static final ServiceStatus HTTP_BAD_REQUEST = new ServiceStatus(400, "请求处理错误!");

	public static final ServiceStatus HTTP_UNAUTHORIZED = new ServiceStatus(401, "请求用户未授权!");

	public static final ServiceStatus HTTP_FORBIDDEN = new ServiceStatus(403, "请求用户无访问权限!");

	public static final ServiceStatus HTTP_NOT_FOUND = new ServiceStatus(404, "请求服务地址不存在!");

	public static final ServiceStatus HTTP_METHOD_NOT_ALLOWED = new ServiceStatus(405, "请求服务方法错误!");

	public static final ServiceStatus HTTP_NOT_ACCEPTABLE = new ServiceStatus(406, "请求服务内容错误!");

	public static final ServiceStatus HTTP_REQUEST_TIMEOUT = new ServiceStatus(408, "请求服务处理超时!");

	public static final ServiceStatus HTTP_CONFLICT = new ServiceStatus(409, "请求服务内容冲突!");

	public static final ServiceStatus HTTP_GONE = new ServiceStatus(410, "请求服务不可用!");

	public static final ServiceStatus HTTP_LENGTH_REQUIRED = new ServiceStatus(411, "请求内容长度未定义!");

	public static final ServiceStatus HTTP_PAYLOAD_TOO_LARGE = new ServiceStatus(413, "请求内容超出长度!");

	public static final ServiceStatus HTTP_URI_TOO_LONG = new ServiceStatus(414, "请求地址超出长度!");

	public static final ServiceStatus HTTP_UNSUPPORTED_MEDIA_TYPE = new ServiceStatus(415, "请求内容类型错误!");

	public static final ServiceStatus HTTP_UNPROCESSABLE_ENTITY = new ServiceStatus(422, "请求语义有误!");

	public static final ServiceStatus HTTP_TOO_MANY_REQUESTS = new ServiceStatus(429, "请求超出连接数限制!");

	// http 5xx 状态码

	public static final ServiceStatus HTTP_INTERNAL_SERVER_ERROR = new ServiceStatus(500, "服务内部错误!");

	public static final ServiceStatus HTTP_NOT_IMPLEMENTED = new ServiceStatus(501, "不支持当前请求!");

	public static final ServiceStatus HTTP_BAD_GATEWAY = new ServiceStatus(502, "网关处理错误!");

	public static final ServiceStatus HTTP_SERVICE_UNAVAILABLE = new ServiceStatus(503, "服务暂时不可用!");

	public static final ServiceStatus HTTP_GATEWAY_TIMEOUT = new ServiceStatus(504, "网关处理超时!");

	public static final ServiceStatus HTTP_VERSION_NOT_SUPPORTED = new ServiceStatus(505, "不支持当前协议版本号!");

	// 基于 http 状态码，自定义业务状态码

	public static final ServiceStatus EXTEND_MISSING_HEADER_ERROR = HTTP_BAD_REQUEST.newInstance("请求服务缺失必要头信息!");

	public static final ServiceStatus EXTEND_URI_NOT_ALLOWED = HTTP_NOT_FOUND.newInstance("请求服务地址不允许访问!");

	public static final ServiceStatus EXTEND_PARAM_BIND_ERROR = HTTP_NOT_ACCEPTABLE.newInstance("请求服务参数绑定失败!");

	public static final ServiceStatus EXTEND_PARAM_VALID_ERROR = HTTP_NOT_ACCEPTABLE.newInstance("请求服务参数验证失败!");

	public static final ServiceStatus EXTEND_PARAM_RESOLVE_ERROR = HTTP_NOT_ACCEPTABLE.newInstance("请求服务参数解析失败!");

	public static final ServiceStatus EXTEND_NOT_LOGIN = HTTP_UNAUTHORIZED.newInstance("用户未登录!");
	
	public static final ServiceStatus EXTEND_EXPIRED_LOGIN = HTTP_UNAUTHORIZED.newInstance("用户登录信息已过期!");
	
	public static final ServiceStatus EXTEND_ROLE_NOT_EXIST = HTTP_FORBIDDEN.newInstance("用户所属角色不存在!");
	
	public static final ServiceStatus EXTEND_ROLE_DISABLED = HTTP_FORBIDDEN.newInstance("用户所属角色已被禁用!");

	public static final ServiceStatus EXTEND_SERVER_ERROR = HTTP_INTERNAL_SERVER_ERROR.newInstance("请求服务错误，请稍后再试!");

	public static final ServiceStatus EXTEND_SERVER_BUSY_ERRROR = HTTP_INTERNAL_SERVER_ERROR.newInstance("请求服务繁忙，请稍后再试!");

	public static final ServiceStatus EXTEND_SERVER_FREQUENCY_TOO_QUICKLY = HTTP_INTERNAL_SERVER_ERROR.newInstance("请求频率过快，请稍后再试!");

	// 状态码 class 定义

	private static final long serialVersionUID = -8828294891900187085L;

	private final int errcode;

	private final String errmsg;

	private ServiceStatus(int errcode, String errmsg) {
		super();
		if(errmsg == null) {
			throw new IllegalArgumentException("errmsg");
		}
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	/**
	 * 使用当前对象，创建一个指定 errmsg 对象
	 * 
	 * @param errmsg
	 * @return
	 */
	public ServiceStatus newInstance(String errmsg) {
		return new ServiceStatus(this.errcode, errmsg);
	}

	/**
	 * 获取状态码
	 * 
	 * @return
	 */
	public int getErrcode() {
		return errcode;
	}

	/**
	 * 获取状态信息
	 * 
	 * @return
	 */
	public String getErrmsg() {
		return errmsg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[errcode=");
		builder.append(errcode);
		builder.append(", errmsg=");
		builder.append(errmsg);
		builder.append("]");
		return builder.toString();
	}

}
