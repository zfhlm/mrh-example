package org.lushen.mrh.cloud.service.organ.config;

import java.net.BindException;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.reference.supports.ServiceStatus;
import org.lushen.mrh.cloud.reference.supports.ServiceStatusException;
import org.lushen.mrh.cloud.reference.supports.feign.decoder.InnerServerErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.util.SerializationUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

/**
 * 全局异常处理器
 * 
 * @author hlm
 */
@ControllerAdvice
public class GlobalControllerAdvice {

	private final Log log = LogFactory.getLog(getClass());

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<byte[]> handle(Throwable cause) {

		log.error(cause.getMessage(), cause);

		Supplier<InnerServerErrorBody> builder = () -> {
			// 状态码异常
			if(cause instanceof ServiceStatusException) {
				return new InnerServerErrorBody(((ServiceStatusException)cause).getStatus(), Boolean.TRUE);
			}
			// 参数绑定错误
			else if(cause instanceof BindException) {
				return new InnerServerErrorBody(ServiceStatus.EXTEND_PARAM_BIND_ERROR, Boolean.TRUE);
			}
			// 参数验证错误
			else if(cause instanceof MethodArgumentNotValidException) {
				return new InnerServerErrorBody(ServiceStatus.EXTEND_PARAM_VALID_ERROR, Boolean.TRUE);
			}
			// 参数解析错误
			else if(cause instanceof HttpMessageConversionException) {
				return new InnerServerErrorBody(ServiceStatus.EXTEND_PARAM_RESOLVE_ERROR, Boolean.TRUE);
			}
			// 请求接口不存在
			else if(cause instanceof NoHandlerFoundException) {
				return new InnerServerErrorBody(ServiceStatus.HTTP_NOT_FOUND, Boolean.TRUE);
			}
			// 请求方法错误
			else if(cause instanceof HttpRequestMethodNotSupportedException) {
				return new InnerServerErrorBody(ServiceStatus.HTTP_METHOD_NOT_ALLOWED, Boolean.TRUE);
			}
			// 请求头缺失
			else if(cause instanceof MissingRequestHeaderException) {
				return new InnerServerErrorBody(ServiceStatus.EXTEND_MISSING_HEADER_ERROR, Boolean.TRUE);
			}
			// 熔断异常，不往上传递
			else if(cause instanceof CallNotPermittedException) {
				return new InnerServerErrorBody(ServiceStatus.EXTEND_SERVER_BUSY_ERRROR, Boolean.TRUE);
			}
			// 超时异常，不往上传递
			else if(cause instanceof TimeoutException) {
				return new InnerServerErrorBody(ServiceStatus.HTTP_REQUEST_TIMEOUT, Boolean.TRUE);
			}
			// 其他错误，非业务异常
			else {
				return new InnerServerErrorBody(ServiceStatus.EXTEND_SERVER_ERROR, Boolean.FALSE);
			}
		};

		return new ResponseEntity<byte[]>(SerializationUtils.serialize(builder.get()), HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
