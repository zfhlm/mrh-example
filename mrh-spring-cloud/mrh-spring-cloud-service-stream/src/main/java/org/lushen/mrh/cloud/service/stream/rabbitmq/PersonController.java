package org.lushen.mrh.cloud.service.stream.rabbitmq;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	@Autowired
	private StreamBridge streamBridge;

	@GetMapping(path="user")
	public String user() {
		Person person = new Person();
		person.setName(UUID.randomUUID().toString());
		streamBridge.send("user-producer-out-0", person);
		return "success";
	}

	@GetMapping(path="order")
	public String order() {
		Person person = new Person();
		person.setName(UUID.randomUUID().toString());
		streamBridge.send("order-producer-out-0", person);
		return "success";
	}

}
