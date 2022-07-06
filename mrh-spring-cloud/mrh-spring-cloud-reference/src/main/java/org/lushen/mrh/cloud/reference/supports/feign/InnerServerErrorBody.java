package org.lushen.mrh.cloud.reference.supports.feign;

import java.io.Serializable;

import org.lushen.mrh.cloud.reference.supports.ServiceBusinessException;
import org.lushen.mrh.cloud.reference.supports.ServiceStatusException;
import org.lushen.mrh.cloud.reference.supports.ServiceStatus;

/**
 * 用于服务错误响应，根据当前对象决定抛出 {@link ServiceStatusException} 或 {@link ServiceBusinessException}
 * 
 * @author hlm
 */
public class InnerServerErrorBody implements Serializable {

	private static final long serialVersionUID = -5482255815368673558L;

	private ServiceStatus status;			// 错误状态码

	private boolean isBusiness;				// 是否业务错误

	public InnerServerErrorBody(ServiceStatus status, boolean isBusiness) {
		super();
		this.status = status;
		this.isBusiness = isBusiness;
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

	public boolean isBusiness() {
		return isBusiness;
	}

	public void setBusiness(boolean isBusiness) {
		this.isBusiness = isBusiness;
	}

}
