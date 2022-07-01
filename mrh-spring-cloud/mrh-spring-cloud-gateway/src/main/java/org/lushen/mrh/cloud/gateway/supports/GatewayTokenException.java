package org.lushen.mrh.cloud.gateway.supports;

/**
 * 令牌处理异常
 * 
 * @author hlm
 */
public class GatewayTokenException extends Exception {

	private static final long serialVersionUID = 3156013436270961981L;

	public GatewayTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public GatewayTokenException(String message) {
		super(message);
	}

	public GatewayTokenException(Throwable cause) {
		super(cause);
	}

	/**
	 * 令牌超时异常
	 * 
	 * @author hlm
	 */
	public static class GatewayTokenExpiredException extends GatewayTokenException {

		private static final long serialVersionUID = 4185473174414439671L;

		public GatewayTokenExpiredException(Throwable cause) {
			super(cause);
		}

		public GatewayTokenExpiredException(String message, Throwable cause) {
			super(message, cause);
		}

		public GatewayTokenExpiredException(String message) {
			super(message);
		}

	}

}
