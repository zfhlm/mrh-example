package org.lushen.mrh.cloud.gateway.supports;

/**
 * 网关异常转换接口
 * 
 * @author hlm
 */
public interface GatewayExceptionConverter {

	/**
	 * 转换异常为 Json byte array
	 * 
	 * @param cause
	 * @return
	 */
	public byte[] toJsonByteArray(Throwable cause);

}
