package org.lushen.mrh.cloud.service.bus.amqp;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 测试事件
 * 
 * @author hlm
 */
public class TestRemoteEvent extends RemoteApplicationEvent {

	private static final long serialVersionUID = 1788794459100508149L;

	@SuppressWarnings("unused")
	private TestRemoteEvent() {
		super();
	}

	public TestRemoteEvent(Object source, String originService, Destination destination) {
		super(source, originService, destination);
	}

}
