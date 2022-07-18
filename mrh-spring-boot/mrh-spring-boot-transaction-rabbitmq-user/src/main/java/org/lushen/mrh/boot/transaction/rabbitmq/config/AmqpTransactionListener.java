package org.lushen.mrh.boot.transaction.rabbitmq.config;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.transaction.rabbitmq.dao.mapper.TUserMapper;
import org.lushen.mrh.boot.transaction.rabbitmq.dao.model.TUser;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * 消息监听器
 * 
 * @author hlm
 */
@Component
@RabbitListener(queues = AmqpConfiguration.TRANSACTION_QUEUE)
public class AmqpTransactionListener {

	private final Log log = LogFactory.getLog(getClass());

	private final RestTemplate template = new RestTemplate();

	@Autowired
	private TUserMapper userMapper;

	@RabbitHandler
	@Transactional
	public void handle(AmqpParameter parameter) {

		log.info("message :" + parameter);

		log.info("add :: " + parameter.getId());

		// 添加用户
		TUser user = new TUser();
		user.setId(parameter.getId());
		user.setName(parameter.getName());
		userMapper.insert(user);

		// 添加积分
		template.getForEntity("http://localhost:8888/add/"+parameter.getId(), String.class);
		
		// 模拟错误
		if(ThreadLocalRandom.current().nextInt(20) > 1) {
			throw new RuntimeException("test");
		}

	}

}