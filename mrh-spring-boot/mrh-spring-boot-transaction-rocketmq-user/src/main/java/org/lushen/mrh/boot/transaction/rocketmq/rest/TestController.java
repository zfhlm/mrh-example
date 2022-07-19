package org.lushen.mrh.boot.transaction.rocketmq.rest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.lushen.mrh.boot.transaction.rocketmq.config.TestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事务消息发送接口
 * 
 * @author hlm
 */
@RestController
public class TestController {

	@Autowired
	private RocketMQTemplate mqTemplate;

	@RequestMapping(path="publish")
	public String user() throws Exception {

		// 事务消息实体
		TestPayload payload = new TestPayload();
		payload.setId(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
		payload.setName(UUID.randomUUID().toString());

		// 发送事务消息
		Message<TestPayload> message = MessageBuilder.withPayload(payload).setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
		TransactionSendResult result = mqTemplate.sendMessageInTransaction("transaction-dest", message, null);

		// 发送失败
		if(result.getSendStatus() != SendStatus.SEND_OK) {
			throw new RuntimeException("send fail :: " + result.getSendStatus());
		}

		return "success";
	}

}
