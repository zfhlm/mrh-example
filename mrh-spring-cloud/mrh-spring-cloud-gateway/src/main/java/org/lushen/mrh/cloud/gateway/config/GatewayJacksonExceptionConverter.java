package org.lushen.mrh.cloud.gateway.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.gateway.supports.GatewayExceptionConverter;
import org.lushen.mrh.cloud.reference.supports.StatusCode;
import org.lushen.mrh.cloud.reference.supports.StatusCodeException;
import org.lushen.mrh.cloud.reference.supports.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

/**
 * 网关异常转换实现
 * 
 * @author hlm
 */
@Component
public class GatewayJacksonExceptionConverter implements GatewayExceptionConverter {

	private final Log log = LogFactory.getLog(GatewayJacksonExceptionConverter.class.getSimpleName());

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public byte[] toJsonByteArray(Throwable cause) {
		// 业务异常
		if(cause instanceof StatusCodeException) {
			StatusCode statusCode = ((StatusCodeException)cause).getErrorReason();
			return toJson(ViewResult.create(statusCode.getErrcode(), statusCode.getErrmsg()));
		}
		// 熔断异常
		else if(cause instanceof CallNotPermittedException) {
			log.error(cause.getMessage());
			return toJson(ViewResult.create(StatusCode.SERVER_BUSINESS));
		}
		// 其他异常
		else {
			log.error(cause.getMessage(), cause);
			return toJson(ViewResult.create(StatusCode.SERVER_ERROR.getErrcode(), cause.getMessage()));
		}
	}

	private byte[] toJson(ViewResult result) {
		try {
			return objectMapper.writeValueAsBytes(result);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}