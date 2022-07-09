package org.lushen.mrh.cloud.service.stream.rabbitmq;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfiguration {

	//	@Bean("user-producer")
	//	public Supplier<Person> userProducer() {
	//		return () -> {
	//			Person person = new Person();
	//			person.setName(UUID.randomUUID().toString());
	//			return person;
	//		};
	//	}

	@Bean("user-consumer")
	public Consumer<Person> userConsumer() {
		return person -> {
			System.out.println("Received user : " + person);
		};
	}

	//	@Bean("order-producer")
	//	public Supplier<Person> orderProducer() {
	//		return () -> {
	//			Person person = new Person();
	//			person.setName(UUID.randomUUID().toString());
	//			return person;
	//		};
	//	}

	@Bean("order-consumer")
	public Consumer<Person> orderConsumer() {
		return person -> {
			System.out.println("Received order: " + person);
		};
	}

}
