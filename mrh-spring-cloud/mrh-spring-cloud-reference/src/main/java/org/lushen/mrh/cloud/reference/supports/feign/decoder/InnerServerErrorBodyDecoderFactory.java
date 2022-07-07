package org.lushen.mrh.cloud.reference.supports.feign.decoder;

import java.nio.ByteBuffer;

import org.lushen.mrh.cloud.reference.supports.ServiceBusinessException;
import org.lushen.mrh.cloud.reference.supports.ServiceStatusException;
import org.lushen.mrh.cloud.reference.supports.ServiceStatus;
import org.springframework.cloud.openfeign.FeignErrorDecoderFactory;
import org.springframework.util.SerializationUtils;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import feign.FeignException.FeignServerException;
import feign.Response;
import feign.RetryableException;
import feign.codec.DecodeException;
import feign.codec.EncodeException;
import feign.codec.ErrorDecoder;

/**
 * feign 异常处理器工厂实现，当 http 500 时，使用 jdk 反序列化 body 生成 {@link ServiceStatus} 并抛出异常
 * 
 * @author hlm
 */
public class InnerServerErrorBodyDecoderFactory extends ErrorDecoder.Default implements FeignErrorDecoderFactory {

	@Override
	public ErrorDecoder create(Class<?> type) {
		return this;
	}

	@Override
	public Exception decode(String methodKey, Response response) {

		FeignException cause = (FeignException)super.decode(methodKey, response);

		if(cause instanceof FeignServerException) {
			if(cause instanceof FeignException.InternalServerError) {
				byte[] body = cause.responseBody().map(ByteBuffer::array).orElse(null);
				if(body != null) {
					try {
						// 根据响应内容，决定是否返回业务异常
						InnerServerErrorBody errorBody = (InnerServerErrorBody)SerializationUtils.deserialize(body);
						if(errorBody.isBusiness()) {
							return new ServiceBusinessException(errorBody.getStatus(), methodKey);
						} else {
							return new ServiceStatusException(errorBody.getStatus(), methodKey);
						}
					} catch (Exception ex) {}
				}
				return new ServiceStatusException(ServiceStatus.HTTP_INTERNAL_SERVER_ERROR, cause);
			}
			// 非业务异常
			else if(cause instanceof FeignException.BadGateway) {
				return new ServiceStatusException(ServiceStatus.HTTP_BAD_GATEWAY, cause);
			}
			// 非业务异常
			else if(cause instanceof FeignException.GatewayTimeout) {
				return new ServiceStatusException(ServiceStatus.HTTP_GATEWAY_TIMEOUT, cause);
			}
			// 非业务异常
			else if(cause instanceof FeignException.NotImplemented) {
				return new ServiceStatusException(ServiceStatus.HTTP_NOT_IMPLEMENTED, cause);
			}
			// 非业务异常
			else if(cause instanceof FeignException.ServiceUnavailable) {
				return new ServiceStatusException(ServiceStatus.HTTP_SERVICE_UNAVAILABLE, cause);
			}
		}
		else if(cause instanceof FeignClientException) {
			if(cause instanceof FeignException.BadRequest) {
				return new ServiceBusinessException(ServiceStatus.HTTP_BAD_REQUEST, cause);
			}
			else if(cause instanceof FeignException.Conflict) {
				return new ServiceBusinessException(ServiceStatus.HTTP_CONFLICT, cause);
			}
			else if(cause instanceof FeignException.Forbidden) {
				return new ServiceBusinessException(ServiceStatus.HTTP_FORBIDDEN, cause);
			}
			else if(cause instanceof FeignException.Gone) {
				return new ServiceBusinessException(ServiceStatus.HTTP_GONE, cause);
			}
			else if(cause instanceof FeignException.MethodNotAllowed) {
				return new ServiceBusinessException(ServiceStatus.HTTP_METHOD_NOT_ALLOWED, cause);
			}
			else if(cause instanceof FeignException.NotAcceptable) {
				return new ServiceBusinessException(ServiceStatus.HTTP_NOT_ACCEPTABLE, cause);
			}
			else if(cause instanceof FeignException.NotFound) {
				return new ServiceBusinessException(ServiceStatus.HTTP_NOT_FOUND, cause);
			}
			// 非业务异常
			else if(cause instanceof FeignException.TooManyRequests) {
				return new ServiceStatusException(ServiceStatus.EXTEND_SERVER_BUSY_ERRROR, cause);
			}
			else if(cause instanceof FeignException.Unauthorized) {
				return new ServiceBusinessException(ServiceStatus.HTTP_UNAUTHORIZED, cause);
			}
			else if(cause instanceof FeignException.UnprocessableEntity) {
				return new ServiceBusinessException(ServiceStatus.HTTP_UNPROCESSABLE_ENTITY, cause);
			}
			else if(cause instanceof FeignException.UnsupportedMediaType) {
				return new ServiceBusinessException(ServiceStatus.HTTP_UNSUPPORTED_MEDIA_TYPE, cause);
			}
		}
		// 非业务异常
		else if(cause instanceof RetryableException) {
			return new ServiceStatusException(ServiceStatus.HTTP_SERVICE_UNAVAILABLE, cause);
		}
		else if(cause instanceof EncodeException) {
			return new ServiceBusinessException(ServiceStatus.HTTP_BAD_REQUEST, cause);
		}
		else if(cause instanceof DecodeException) {
			return new ServiceBusinessException(ServiceStatus.HTTP_BAD_REQUEST, cause);
		}

		return cause;
	}

}
