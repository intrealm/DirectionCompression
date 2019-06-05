package com.example.demo.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfig {

	@Bean(name = "CompressionThreadPool")
	public TaskExecutor compressionThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("compression_task_thread");
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(4);
		executor.initialize();
		return executor;
	}

	@Bean(name = "DecompressionThreadPool")
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setThreadNamePrefix("decompression_task_thread");
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(4);
		executor.initialize();
		return executor;
	}

}
