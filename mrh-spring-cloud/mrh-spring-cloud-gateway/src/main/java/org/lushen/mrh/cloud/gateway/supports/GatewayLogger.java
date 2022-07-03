package org.lushen.mrh.cloud.gateway.supports;

import org.apache.commons.logging.Log;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.csp.sentinel.util.function.BiConsumer;

/**
 * 网关日志包装接口
 * 
 * @author hlm
 */
public interface GatewayLogger {

	public boolean isFatalEnabled();

	public boolean isErrorEnabled();

	public boolean isWarnEnabled();

	public boolean isInfoEnabled();

	public boolean isDebugEnabled();

	public boolean isTraceEnabled();

	public void fatal(ServerWebExchange exchange, Object message);

	public void fatal(ServerWebExchange exchange, Object message, Throwable t);

	public void error(ServerWebExchange exchange, Object message);

	public void error(ServerWebExchange exchange, Object message, Throwable t);

	public void warn(ServerWebExchange exchange, Object message);

	public void warn(ServerWebExchange exchange, Object message, Throwable t);

	public void info(ServerWebExchange exchange, Object message);

	public void info(ServerWebExchange exchange, Object message, Throwable t);

	public void debug(ServerWebExchange exchange, Object message);

	public void debug(ServerWebExchange exchange, Object message, Throwable t);

	public void trace(ServerWebExchange exchange, Object message);

	public void trace(ServerWebExchange exchange, Object message, Throwable t);

	/**
	 * 网关日志包装类
	 * 
	 * @author hlm
	 */
	public static class GatewayLoggerDecorator implements GatewayLogger {

		private final Log delegate;
		
		private final BiConsumer<ServerWebExchange, Runnable> executor;

		public GatewayLoggerDecorator(Log delegate, BiConsumer<ServerWebExchange, Runnable> executor) {
			super();
			this.delegate = delegate;
			this.executor = executor;
		}

		@Override
		public void fatal(ServerWebExchange exchange, Object message) {
			this.executor.accept(exchange, () -> this.delegate.fatal(message));
		}

		@Override
		public void fatal(ServerWebExchange exchange, Object message, Throwable t) {
			this.executor.accept(exchange, () -> this.delegate.fatal(message, t));
		}

		@Override
		public void error(ServerWebExchange exchange, Object message) {
			this.executor.accept(exchange, () -> this.delegate.error(message));
		}

		@Override
		public void error(ServerWebExchange exchange, Object message, Throwable t) {
			this.executor.accept(exchange, () -> this.delegate.error(message, t));
		}

		@Override
		public void warn(ServerWebExchange exchange, Object message) {
			this.executor.accept(exchange, () -> this.delegate.warn(message));
		}

		@Override
		public void warn(ServerWebExchange exchange, Object message, Throwable t) {
			this.executor.accept(exchange, () -> this.delegate.warn(message, t));
		}

		@Override
		public void info(ServerWebExchange exchange, Object message) {
			this.executor.accept(exchange, () -> this.delegate.info(message));
		}

		@Override
		public void info(ServerWebExchange exchange, Object message, Throwable t) {
			this.executor.accept(exchange, () -> this.delegate.info(message, t));
		}

		@Override
		public void debug(ServerWebExchange exchange, Object message) {
			this.executor.accept(exchange, () -> this.delegate.debug(message));
		}

		@Override
		public void debug(ServerWebExchange exchange, Object message, Throwable t) {
			this.executor.accept(exchange, () -> this.delegate.debug(message, t));
		}

		@Override
		public void trace(ServerWebExchange exchange, Object message) {
			this.executor.accept(exchange, () -> this.delegate.trace(message));
		}

		@Override
		public void trace(ServerWebExchange exchange, Object message, Throwable t) {
			this.executor.accept(exchange, () -> this.delegate.trace(message, t));
		}

		@Override
		public boolean isFatalEnabled() {
			return this.delegate.isFatalEnabled();
		}

		@Override
		public boolean isErrorEnabled() {
			return this.delegate.isErrorEnabled();
		}

		@Override
		public boolean isWarnEnabled() {
			return this.delegate.isWarnEnabled();
		}

		@Override
		public boolean isInfoEnabled() {
			return this.delegate.isInfoEnabled();
		}

		@Override
		public boolean isDebugEnabled() {
			return this.delegate.isDebugEnabled();
		}

		@Override
		public boolean isTraceEnabled() {
			return this.delegate.isTraceEnabled();
		}

	}
	
}
