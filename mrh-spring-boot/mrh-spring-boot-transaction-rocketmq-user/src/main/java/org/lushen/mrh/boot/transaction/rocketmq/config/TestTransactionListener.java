package org.lushen.mrh.boot.transaction.rocketmq.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 事务消息监听器
 * 
 * @author hlm
 */
@RocketMQTransactionListener
@SuppressWarnings("rawtypes")
public class TestTransactionListener implements RocketMQLocalTransactionListener {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private TestService testService;

	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

		try {

			TestPayload payload = objectMapper.readValue((byte[])msg.getPayload(), TestPayload.class);

			log.info("execute transaction :: " + payload);

			testService.add(payload);

			return RocketMQLocalTransactionState.COMMIT;

		} catch (Exception e) {

			log.warn(e.getMessage(), e);

			return RocketMQLocalTransactionState.ROLLBACK;

		}

	}

	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {

		try {

			TestPayload payload = objectMapper.readValue((byte[])msg.getPayload(), TestPayload.class);

			log.info("check transaction :: " + payload);

			if(testService.check(payload)) {
				return RocketMQLocalTransactionState.COMMIT;
			} else {
				return RocketMQLocalTransactionState.ROLLBACK;
			}

		} catch (Exception e) {

			log.warn(e.getMessage(), e);

			return RocketMQLocalTransactionState.UNKNOWN;

		}
	}

}
