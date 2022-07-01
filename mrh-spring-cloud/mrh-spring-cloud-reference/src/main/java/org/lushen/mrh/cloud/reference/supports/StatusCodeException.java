package org.lushen.mrh.cloud.reference.supports;

/**
 * 错误码异常
 * 
 * @author hlm
 */
@SuppressWarnings("serial")
public class StatusCodeException extends RuntimeException {

	private StatusCode statusCode;

	public StatusCodeException() {
		super();
	}

	public StatusCodeException(StatusCode statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public StatusCodeException(StatusCode statusCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public StatusCodeException(StatusCode statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public StatusCodeException(StatusCode statusCode, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	@Override
	public String getMessage() {
		return statusCode.getErrmsg();
	}

}
