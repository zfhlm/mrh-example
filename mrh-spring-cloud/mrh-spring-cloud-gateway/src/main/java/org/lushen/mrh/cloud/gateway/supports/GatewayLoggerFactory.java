package org.lushen.mrh.cloud.gateway.supports;

import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.WebFluxSleuthOperators;

/**
 * 适配 sleuth 使用 spring.sleuth.reactor.instrumentation-type=MANUAL 手动控制日志打印
 * 
 * @author hlm
 */
public abstract class GatewayLoggerFactory {

	/**
	 * 创建日志
	 * 
	 * @param clazz
	 * @return
	 */
	public static GatewayLogger getLog(Class<?> clazz) {
		return getLog(clazz.getName());
	}

	/**
	 * 创建日志
	 * 
	 * @param name
	 * @return
	 */
	public static GatewayLogger getLog(String name) {
		return new GatewayLogger.GatewayLoggerDecorator(LogFactory.getLog(name), (exchange, runnable) -> {
			Tracer tracer = GatewayExchangeUtils.Sleuth.tracer();
			CurrentTraceContext currentTraceContext = GatewayExchangeUtils.Sleuth.currentTraceContext();
			if(tracer != null && currentTraceContext != null) {
				WebFluxSleuthOperators.withSpanInScope(tracer, currentTraceContext, exchange, runnable);
			} else {
				runnable.run();
			}
		});
	}

}
