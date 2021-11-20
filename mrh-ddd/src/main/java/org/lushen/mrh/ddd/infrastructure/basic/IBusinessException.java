package org.lushen.mrh.ddd.infrastructure.basic;

/**
 * 业务异常
 * 
 * @author hlm
 */
public class IBusinessException extends RuntimeException {

	private static final long serialVersionUID = 3988461758856917986L;

	public IBusinessException() {
		super();
	}

	public IBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public IBusinessException(String message) {
		super(message);
	}

	public IBusinessException(Throwable cause) {
		super(cause);
	}

}
