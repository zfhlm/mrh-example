package org.lushen.mrh.cloud.reference.supports;

/**
 * 服务器错误异常
 * 
 * @author hlm
 */
public class StatusCodeServerException extends StatusCodeException {

	private static final long serialVersionUID = -3673897540309505961L;

	public StatusCodeServerException() {
		super();
	}

	public StatusCodeServerException(String message) {
		super(new StatusCode(StatusCode.SERVER_ERROR.getErrcode(), message), message);
	}

	public StatusCodeServerException(Throwable cause) {
		super(new StatusCode(StatusCode.SERVER_ERROR.getErrcode(), cause.getMessage()), cause);
	}

	public StatusCodeServerException(String message, Object... args) {
		super(new StatusCode(StatusCode.SERVER_ERROR.getErrcode(), String.format(message, args)), message);
	}

	public StatusCodeServerException(String message, Throwable cause) {
		super(new StatusCode(StatusCode.SERVER_ERROR.getErrcode(), message), message, cause);
	}

}
