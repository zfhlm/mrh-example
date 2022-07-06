package org.lushen.mrh.cloud.reference.supports;

/**
 * 状态码异常
 * 
 * @author hlm
 */
public class ServiceStatusException extends RuntimeException {

	private static final long serialVersionUID = -7831210714206422980L;

	private ServiceStatus status;

	public ServiceStatusException(ServiceStatus status) {
		super();
		this.status = status;
	}

	public ServiceStatusException(ServiceStatus status, String message) {
		super(message);
		this.status = status;
	}

	public ServiceStatusException(ServiceStatus status, Throwable cause) {
		super(cause);
		this.status = status;
	}

	/**
	 * 获取异常状态码
	 * 
	 * @return
	 */
	public ServiceStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return String.valueOf(this.status);
	}

}
