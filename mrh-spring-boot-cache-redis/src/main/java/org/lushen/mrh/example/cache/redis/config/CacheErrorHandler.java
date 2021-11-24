package org.lushen.mrh.example.cache.redis.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.Cache;

/**
 * 缓存错误处理器
 * 
 * @author hlm
 */
public class CacheErrorHandler implements org.springframework.cache.interceptor.CacheErrorHandler {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		rethrowOrDoLog(exception);
	}

	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
		rethrowOrDoLog(exception);
	}

	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
		rethrowOrDoLog(exception);
	}

	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		rethrowOrDoLog(exception);
	}

	private void rethrowOrDoLog(RuntimeException cause) {
		log.error(cause.getMessage());
		if(cause instanceof NotSupportCommandException) {
			throw cause;
		}
	}

	@SuppressWarnings("serial")
	static class NotSupportCommandException extends RuntimeException {

		public NotSupportCommandException(String message) {
			super(message);
		}

	}

}
