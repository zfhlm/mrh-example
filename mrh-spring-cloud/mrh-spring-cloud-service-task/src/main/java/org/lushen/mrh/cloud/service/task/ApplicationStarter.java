package org.lushen.mrh.cloud.service.task;

import java.util.Arrays;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.listener.annotation.FailedTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackageClasses=ApplicationStarter.class)
@EnableTransactionManagement(proxyTargetClass=true)
@EnableDiscoveryClient
@EnableTask
public class ApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}

	//@Bean
	public CommandLineRunner commandLineRunner() {
		return new HelloWorldCommandLineRunner();
	}

	@Bean
	public MyBean myBean() {
		return new MyBean();
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				System.out.println("Hello, World!" + args);
			}
		};
	}

	public static class HelloWorldCommandLineRunner implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
			System.out.println("Hello, World!" + Arrays.toString(args));
		}

	}

	public class MyBean {

		@BeforeTask
		public void methodA(TaskExecution taskExecution) {
			System.out.println("===@BeforeTask ===");
		}

		@AfterTask
		public void methodB(TaskExecution taskExecution) {
			System.out.println("===@AfterTask ===");
		}

		@FailedTask
		public void methodC(TaskExecution taskExecution, Throwable throwable) {
			System.out.println("===@FailedTask ===");
		}
	}

}
