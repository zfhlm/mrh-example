package org.lushen.mrh.cloud.reference.supports;

import java.io.Serializable;

/**
 * 错误码信息对象
 * 
 * @author hlm
 */
@SuppressWarnings("serial")
public class StatusCode implements Serializable {
	
	public static final StatusCode SUCCESS = new StatusCode(0, "成功!");

	public static final StatusCode API_NOT_FOUND = new StatusCode(10101, "请求接口不存在!");
	
	public static final StatusCode API_NOT_ALLOWED = new StatusCode(10102, "请求接口不允许访问!");

	public static final StatusCode API_NOT_ACCEPTABLE = new StatusCode(10103, "请求接口方式错误!");

	public static final StatusCode API_NOT_AVAILABLE = new StatusCode(10104, "请求接口不可用!");

	public static final StatusCode API_REQUEST_TIMEOUT = new StatusCode(10105, "请求接口超时，请稍后再试!");

	public static final StatusCode API_PARAMETER_ERROR = new StatusCode(10106, "请求接口参数错误!");

	public static final StatusCode SERVER_BUSINESS = new StatusCode(10201, "系统繁忙，请稍后再试!");

	public static final StatusCode SERVER_ERROR = new StatusCode(10202, "系统错误，请稍后再试!");

	public static final StatusCode USER_NOT_LOGIN = new StatusCode(10301, "当前用户未登录!");

	public static final StatusCode USER_EXPIRED_LOGIN = new StatusCode(10302, "当前用户登录信息已过期!");

	public static final StatusCode USER_NO_PERMISSION = new StatusCode(10303, "当前用户无访问权限!");

	public static final StatusCode USER_ROLE_NOT_EXIST = new StatusCode(10304, "当前用户所属角色不存在!");

	public static final StatusCode USER_ROLE_DISABLED = new StatusCode(10305, "当前用户所属角色已被禁用!");

	private int errcode;		// 错误码

	private String errmsg;		// 错误信息

	public StatusCode() {
		super();
	}

	public StatusCode(int errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
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
