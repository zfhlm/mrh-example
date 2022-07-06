package org.lushen.mrh.cloud.reference.supports;

/**
 * 状态码业务异常
 * 
 * @author hlm
 */
public class ServiceBusinessException extends ServiceStatusException {

	private static final long serialVersionUID = -6858131776792113970L;

	public ServiceBusinessException(ServiceStatus status, String message) {
		super(status, message);
	}

	public ServiceBusinessException(ServiceStatus status, Throwable cause) {
		super(status, cause);
	}

	public ServiceBusinessException(ServiceStatus status) {
		super(status);
	}

}
