package org.lushen.mrh.cloud.service.bus.amqp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

	@Bean
	public TestProperties testProperties() {
		return new TestProperties();
	}
	
}
