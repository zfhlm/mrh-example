package org.lushen.mrh.cloud.service.bus.amqp;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener implements ApplicationListener<RemoteApplicationEvent> {

	@Override
	public void onApplicationEvent(RemoteApplicationEvent event) {
		System.err.println(event);
	}

}
