package org.lushen.mrh.cloud.reference.supports;

import java.util.Collection;
import java.util.List;

/**
 * 视图信息定义
 * 
 * @author hlm
 */
public class ViewResult {

	/**
	 * 创建视图信息对象
	 * 
	 * @return
	 */
	public static final ViewResult create() {
		return create(ServiceStatus.OK);
	}

	/**
	 * 创建视图信息对象
	 * 
	 * @param status
	 * @return
	 */
	public static final ViewResult create(ServiceStatus status) {
		ViewResult result = new ViewResult();
		result.setErrcode(status.getErrcode());
		result.setErrmsg(status.getErrmsg());
		return result;
	}

	/**
	 * 创建视图信息对象
	 * 
	 * @param data
	 * @return
	 */
	public static final ViewResult create(Object data) {
		ViewResult result = create();
		result.setData(data);
		return result;
	}

	/**
	 * 创建视图信息对象
	 * 
	 * @param datas
	 * @return
	 */
	public static final ViewResult create(Collection<?> datas) {
		ViewResult result = create();
		result.setDatas(datas);
		return result;
	}

	/**
	 * 创建视图信息对象
	 * 
	 * @param datas
	 * @return
	 */
	public static final ViewResult create(Object[] datas) {
		ViewResult result = create();
		result.setDatas(datas);
		return result;
	}

	/**
	 * 创建视图信息对象
	 * 
	 * @param datas
	 * @param total
	 * @return
	 */
	public static final ViewResult create(List<?> datas, long total) {
		ViewResult result = create();
		result.setDatas(datas);
		result.setTotal(total);
		return result;
	}

	private int errcode;		// 错误码

	private String errmsg;		// 错误信息

	private Object data;		// 数据对象

	private Object datas;		// 数据集合对象

	private Long total;			// 总数

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getDatas() {
		return datas;
	}

	public void setDatas(Object datas) {
		this.datas = datas;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[errcode=");
		builder.append(errcode);
		builder.append(", errmsg=");
		builder.append(errmsg);
		builder.append(", data=");
		builder.append(data);
		builder.append(", datas=");
		builder.append(datas);
		builder.append(", total=");
		builder.append(total);
		builder.append("]");
		return builder.toString();
	}

}
