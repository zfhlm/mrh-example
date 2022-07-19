package org.lushen.mrh.boot.transaction.rocketmq.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.lushen.mrh.boot.transaction.rocketmq.dao.mapper.TIntegralMapper;
import org.lushen.mrh.boot.transaction.rocketmq.dao.model.TIntegral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息监听器
 * 
 * @author hlm
 */
@RocketMQMessageListener(consumerGroup="${rocketmq.consumer.group}", topic = "transaction-dest")
@Component
public class RocketMqListener implements RocketMQListener<TestPayload> {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private TIntegralMapper integralMapper;

	@Override
	public void onMessage(TestPayload payload) {

		log.info("receive :: " + payload);

		TIntegral record = new TIntegral();
		record.setId(payload.getId());
		record.setName(payload.getName());
		integralMapper.insert(record);

	}

}
