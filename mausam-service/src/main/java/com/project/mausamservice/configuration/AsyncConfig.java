package com.project.mausamservice.configuration;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	@Bean(name = "fastExecutor")
	public ThreadPoolTaskExecutor fastExecutor() {
		ThreadPoolTaskExecutor fastExecutor = new ThreadPoolTaskExecutor();
		fastExecutor.setCorePoolSize(5);
		fastExecutor.setMaxPoolSize(5);
		fastExecutor.setKeepAliveSeconds(60);
		fastExecutor.setWaitForTasksToCompleteOnShutdown(true);
		fastExecutor.setQueueCapacity(50);
		fastExecutor.setThreadNamePrefix("MausamThread-");
		fastExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		fastExecutor.initialize();
		return fastExecutor;
	}

}
