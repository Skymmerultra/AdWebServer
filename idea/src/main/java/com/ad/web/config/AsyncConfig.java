package com.ad.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync  // 启用异步支持
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数 - 线程池维护的最小线程数量
        executor.setCorePoolSize(5);
        // 最大线程数 - 线程池能容纳的最大线程数量
        executor.setMaxPoolSize(20);
        // 队列容量 - 当线程数达到核心线程数时，新任务将在队列中等待
        executor.setQueueCapacity(100);
        // 线程名前缀
        executor.setThreadNamePrefix("Async-Executor-");
        // 线程空闲时间(秒) - 超过核心线程数的线程在空闲时间达到后被销毁
        executor.setKeepAliveSeconds(60);
        // 拒绝策略 - 当线程池和队列都满时如何处理新任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}