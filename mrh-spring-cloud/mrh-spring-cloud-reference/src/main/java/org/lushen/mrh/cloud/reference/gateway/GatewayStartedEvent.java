package org.lushen.mrh.cloud.reference.gateway;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 网关启动事件
 * 
 * @author hlm
 */
public class GatewayStartedEvent extends RemoteApplicationEvent {

	private static final long serialVersionUID = 379500815613468417L;

	@SuppressWarnings("unused")
	private GatewayStartedEvent() {}

	public GatewayStartedEvent(Object source, String originService, Destination destination) {
		super(source, originService, destination);
	}

}
