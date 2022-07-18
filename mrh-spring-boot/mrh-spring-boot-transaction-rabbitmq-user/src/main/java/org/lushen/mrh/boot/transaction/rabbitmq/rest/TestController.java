package org.lushen.mrh.boot.transaction.rabbitmq.rest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.lushen.mrh.boot.transaction.rabbitmq.config.AmqpConfiguration;
import org.lushen.mrh.boot.transaction.rabbitmq.config.AmqpParameter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping(path="publish")
	public String user() throws Exception {
		AmqpParameter parameter = new AmqpParameter();
		parameter.setId(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		parameter.setName(UUID.randomUUID().toString());
		rabbitTemplate.convertAndSend(AmqpConfiguration.TRANSACTION_EXCHANGE, AmqpConfiguration.TRANSACTION_ROUTING, parameter);
		return "success";
	}

}
