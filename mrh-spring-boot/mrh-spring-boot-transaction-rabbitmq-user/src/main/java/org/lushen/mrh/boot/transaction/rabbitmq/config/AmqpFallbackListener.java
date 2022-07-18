package org.lushen.mrh.boot.transaction.rabbitmq.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.transaction.rabbitmq.dao.mapper.TUserMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * 死信队列监听器
 */
@Component
@RabbitListener(queues=AmqpConfiguration.FALLBACK_QUEUE)
public class AmqpFallbackListener {

	private final Log log = LogFactory.getLog(getClass());

	private final RestTemplate template = new RestTemplate();

	@Autowired
	private TUserMapper userMapper;

	@RabbitHandler
	@Transactional
	public void handle(AmqpParameter parameter) {

		log.info("fallback message :" + parameter);

		log.info("del :: id = " + parameter.getId());

		// 失败回滚

		userMapper.deleteByPrimaryKey(parameter.getId());

		template.getForEntity("http://localhost:8888/del/"+parameter.getId(), String.class);

	}

}
