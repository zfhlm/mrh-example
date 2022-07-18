package org.lushen.mrh.boot.transaction.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 消息配置
 * 
 * @author hlm
 */
@Configuration
public class AmqpConfiguration {

	public static final String TRANSACTION_EXCHANGE = "mrh.transaction.exchange";			//任务交换器

	public static final String TRANSACTION_ROUTING = "mrh.transaction.routing.key";			//任务队列路由

	public static final String TRANSACTION_QUEUE = "mrh.transaction.queue";					//任务队列名称

	public static final String FALLBACK_EXCHANGE = "mrh.transaction.fallback.exchange";		//死信队列交换器

	public static final String FALLBACK_ROUTING = "mrh.transaction.fallback.routing.key";	//死信队列路由

	public static final String FALLBACK_QUEUE = "mrh.transaction.fallback.queue";			//死信队列名称

	/**
	 * 注册任务交换器
	 */
	@Bean
	public Exchange assignExchange() {
		return ExchangeBuilder.directExchange(TRANSACTION_EXCHANGE).durable(true).build();
	}

	/**
	 * 注册任务队列
	 */
	@Bean
	public Queue assignQueue() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-dead-letter-exchange", FALLBACK_EXCHANGE);
		args.put("x-dead-letter-routing-key", FALLBACK_ROUTING);
		return QueueBuilder.durable(TRANSACTION_QUEUE).withArguments(args).build();
	}

	/**
	 * 绑定任务队列和交换器
	 */
	@Bean
	public Binding assignBinding() {
		return new Binding(TRANSACTION_QUEUE, Binding.DestinationType.QUEUE, TRANSACTION_EXCHANGE, TRANSACTION_ROUTING, null);
	}

	/**
	 * 注册死信队列交换器
	 */
	@Bean
	public Exchange fallbackExchange() {
		return ExchangeBuilder.directExchange(FALLBACK_EXCHANGE).durable(true).build();
	}

	/**
	 * 注册死信队列
	 */
	@Bean
	public Queue fallbackQueue() {
		return QueueBuilder.durable(FALLBACK_QUEUE).build();
	}

	/**
	 * 绑定死信队列和交换器
	 */
	@Bean
	public Binding fallbackBinding() {
		return new Binding(FALLBACK_QUEUE, Binding.DestinationType.QUEUE, FALLBACK_EXCHANGE, FALLBACK_ROUTING, null);
	}

}
