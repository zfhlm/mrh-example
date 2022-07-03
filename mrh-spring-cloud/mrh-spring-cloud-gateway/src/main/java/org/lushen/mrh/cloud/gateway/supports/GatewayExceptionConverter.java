package org.lushen.mrh.cloud.gateway.supports;

import org.springframework.web.server.ServerWebExchange;

/**
 * 网关异常转换接口
 * 
 * @author hlm
 */
public interface GatewayExceptionConverter {

	/**
	 * 转换异常为 Json byte array
	 * 
	 * @param exchange
	 * @param cause
	 * @return
	 */
	public byte[] toJsonByteArray(ServerWebExchange exchange, Throwable cause);

}
