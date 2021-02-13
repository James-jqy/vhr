package org.javaboy.vhr.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname ThreadPoolConfig
 * @Description TODO
 * @Date 2021/2/13 19:33
 * @Created by Jieqiyue
 */
@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {
    @Bean("taskPool")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        poolExecutor.setCorePoolSize(5);
        // 最大线程数
        poolExecutor.setMaxPoolSize(15);
        // 队列大小
        poolExecutor.setQueueCapacity(100);
        // 线程最大空闲时间
        poolExecutor.setKeepAliveSeconds(300);
        // 拒绝策略
        poolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程名称前缀
        poolExecutor.setThreadNamePrefix("my-pool-");

        return poolExecutor;
    }

    //配置异常处理机制
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex,method,params)->{
            System.out.println("异步线程执行失败。方法：[{}],异常信息[{}] : "+  method+ ex.getMessage()+ex);
        };
    }
}
