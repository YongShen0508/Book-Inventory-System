package com.stock.bookinventory.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class AsyncConfig {
	@Bean(name = "notificationExecutor")
	public ThreadPoolTaskExecutor notificationExecutor() {

		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(3);
		threadPoolTaskExecutor.setMaxPoolSize(4);
		threadPoolTaskExecutor.setQueueCapacity(25);
		threadPoolTaskExecutor.setThreadNamePrefix("NotificationExecutor-");
		threadPoolTaskExecutor.initialize();

		return threadPoolTaskExecutor;

	}

	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("TaskExecutor-");
		executor.initialize();
		return executor;
	}

}
