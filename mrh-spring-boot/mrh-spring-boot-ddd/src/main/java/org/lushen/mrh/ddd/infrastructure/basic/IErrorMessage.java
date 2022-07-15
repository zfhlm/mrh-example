package org.lushen.mrh.ddd.infrastructure.basic;

/**
 * 返回信息对象
 * 
 * @author hlm
 */
public class IErrorMessage {

	private int errcode;

	private String errmsg;

	private Object data;

	public IErrorMessage(int errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public IErrorMessage(int errcode, String errmsg, Object data) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.data = data;
	}

	public int getErrcode() {
		return errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public Object getData() {
		return data;
	}

}
