package com.yumaolin.deepunderstand.spring;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/** 
 *
 * @author yuml
 * @since 2019年1月29日
 */
@Configuration
@EnableAsync
public class SpingThreadPoolTaskExecutor {
		
	@Bean
	public Executor userThreadPoolExecutor() {
		ThreadPoolTaskExecutor task = new ThreadPoolTaskExecutor();
		task.setCorePoolSize(2);
		task.setMaxPoolSize(4);
		task.setQueueCapacity(10);
		task.setThreadNamePrefix("测试线程");
		task.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		task.setAllowCoreThreadTimeOut(true);
		return task;
	}
}
