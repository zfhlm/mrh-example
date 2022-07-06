package org.lushen.mrh.cloud.gateway.config;

import org.lushen.mrh.cloud.gateway.supports.GatewayExceptionConverter;
import org.lushen.mrh.cloud.gateway.supports.GatewayLogger;
import org.lushen.mrh.cloud.gateway.supports.GatewayLoggerFactory;
import org.lushen.mrh.cloud.reference.supports.ServiceStatusException;
import org.lushen.mrh.cloud.reference.supports.ServiceStatus;
import org.lushen.mrh.cloud.reference.supports.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

/**
 * 网关异常转换实现
 * 
 * @author hlm
 */
@Component
public class GatewayExceptionToJsonConverter implements GatewayExceptionConverter {

	private final GatewayLogger log = GatewayLoggerFactory.getLog(GatewayExceptionToJsonConverter.class.getSimpleName());

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public byte[] toJsonByteArray(ServerWebExchange exchange, Throwable cause) {

		// 输出错误日志
		log.error(exchange, cause.getMessage(), cause);

		// 状态码异常
		if(cause instanceof ServiceStatusException) {
			return toJson(ViewResult.create(((ServiceStatusException)cause).getStatus()));
		}
		// 熔断异常
		else if(cause instanceof CallNotPermittedException) {
			return toJson(ViewResult.create(ServiceStatus.EXTEND_SERVER_BUSY_ERRROR));
		}
		// 限流异常
		else if(cause instanceof ParamFlowException) {
			return toJson(ViewResult.create(ServiceStatus.EXTEND_SERVER_BUSY_ERRROR));
		}
		// 其他异常
		else {
			return toJson(ViewResult.create(ServiceStatus.EXTEND_SERVER_ERROR));
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
