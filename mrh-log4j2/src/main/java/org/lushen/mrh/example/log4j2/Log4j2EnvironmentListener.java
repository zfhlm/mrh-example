package org.lushen.mrh.example.log4j2;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

/**
 * 启动监听器，用于获取上下文对象
 * 
 * @author hlm
 */
public class Log4j2EnvironmentListener implements ApplicationListener<ApplicationEvent>, Ordered {

	private static final AtomicReference<Environment> environmentHolder = new AtomicReference<Environment>();

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ApplicationEnvironmentPreparedEvent) {
			Environment environment = ((ApplicationEnvironmentPreparedEvent)event).getEnvironment();
			environmentHolder.set(environment);
		}
	}

	/**
	 * 设置比日志监听器高的优先级
	 */
	@Override
	public int getOrder() {
		return LoggingApplicationListener.DEFAULT_ORDER - 10;
	}

	/**
	 * 获取 Spring Environment 
	 * 
	 * @return
	 */
	static final Environment getEnvironment() {
		return environmentHolder.get();
	}

}
