package org.lushen.mrh.cloud.service.bus.amqp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private TestProperties properties;

	@GetMapping(path="test")
	public String test() {
		return properties.getName();
	}

	@Autowired
	private ApplicationContext context;
	@Autowired
	private BusProperties busProperties;
	@Autowired
	private Destination.Factory destinationFactory;

	@GetMapping(path="publish")
	public String publish() {
		context.publishEvent(new TestRemoteEvent(this, busProperties.getId(), destinationFactory.getDestination(null)));
		return "success";
	}

}
