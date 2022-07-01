package org.lushen.mrh.cloud.reference.supports;

/**
 * 错误码异常
 * 
 * @author hlm
 */
@SuppressWarnings("serial")
public class StatusCodeException extends RuntimeException {

	private StatusCode errorReason;

	public StatusCodeException() {
		super();
	}

	public StatusCodeException(StatusCode errorReason) {
		super();
		this.errorReason = errorReason;
	}

	public StatusCodeException(StatusCode errorReason, String message, Throwable cause) {
		super(message, cause);
		this.errorReason = errorReason;
	}

	public StatusCodeException(StatusCode errorReason, String message) {
		super(message);
		this.errorReason = errorReason;
	}

	public StatusCodeException(StatusCode errorReason, Throwable cause) {
		super(cause);
		this.errorReason = errorReason;
	}

	public StatusCode getErrorReason() {
		return errorReason;
	}

	@Override
	public String getMessage() {
		return errorReason.getErrmsg();
	}

}
