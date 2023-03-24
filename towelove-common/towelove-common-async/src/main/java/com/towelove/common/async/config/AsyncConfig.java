package com.towelove.common.async.config;

import com.towelove.common.async.handler.GlobalAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author: 张锦标
 * @date: 2023/3/24 10:34
 * AsyncConfig类
 */
@EnableAsync
@AutoConfiguration
public class AsyncConfig implements AsyncConfigurer {
    public static final String CPU_INTENSIVE = "cpu-intensive";
    public static final String IO_INTENSIVE = "io-intensive";
    @Autowired
    private GlobalAsyncExceptionHandler exceptionHandler;

    //@Override
    //public Executor getAsyncExecutor() {
    //    return null;
    //}

    /**
     * 实现 #getAsyncUncaughtExceptionHandler() 方法，
     * 返回我们定义的 GlobalAsyncExceptionHandler 对象。}
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return exceptionHandler;
    }

    //读取CPU密集型线程池的配置
    @Configuration
    public static class CpuIntensiveConfiguration {
        @Bean(name = CPU_INTENSIVE + "-properties")
        @Primary
        @ConfigurationProperties(prefix = "spring.task.cpu-intensive")
        // 读取 spring.task的 配置到 TaskExecutionProperties 对象
        public TaskExecutionProperties taskExecutionProperties() {
            return new TaskExecutionProperties();
        }

        //创建CPU密集型线程池
        @Bean(name = CPU_INTENSIVE)
        public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
            // 创建 TaskExecutorBuilder 对象
            TaskExecutorBuilder builder = createTskExecutorBuilder(this.taskExecutionProperties());
            // 创建 ThreadPoolTaskExecutor 对象
            return builder.build();
        }
    }

    //读取IO密集型线程池配置
    @Configuration
    public static class IOIntensiveConfiguration {
        //创建IO密集型线程池配置对象
        @Bean(name = IO_INTENSIVE + "-properties")
        @ConfigurationProperties(prefix = "spring.task.io-intensive") // 读取 spring.task.execution-two 配置到
        // TaskExecutionProperties 对象
        public TaskExecutionProperties taskExecutionProperties() {
            return new TaskExecutionProperties();
        }
        //配置IO密集型线程池
        @Bean(name = IO_INTENSIVE)
        public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
            // 创建 TaskExecutorBuilder 对象
            TaskExecutorBuilder builder = createTskExecutorBuilder(this.taskExecutionProperties());
            // 创建 ThreadPoolTaskExecutor 对象
            return builder.build();
        }

    }

    private static TaskExecutorBuilder createTskExecutorBuilder(TaskExecutionProperties properties) {
        // Pool 属性
        TaskExecutionProperties.Pool pool = properties.getPool();
        TaskExecutorBuilder builder = new TaskExecutorBuilder();
        builder = builder.queueCapacity(pool.getQueueCapacity());
        builder = builder.corePoolSize(pool.getCoreSize());
        builder = builder.maxPoolSize(pool.getMaxSize());
        builder = builder.allowCoreThreadTimeOut(pool.isAllowCoreThreadTimeout());
        builder = builder.keepAlive(pool.getKeepAlive());
        // Shutdown 属性
        TaskExecutionProperties.Shutdown shutdown = properties.getShutdown();
        builder = builder.awaitTermination(shutdown.isAwaitTermination());
        builder = builder.awaitTerminationPeriod(shutdown.getAwaitTerminationPeriod());
        // 其它基本属性
        builder = builder.threadNamePrefix(properties.getThreadNamePrefix());
//        builder = builder.customizers(taskExecutorCustomizers.orderedStream()::iterator);
//        builder = builder.taskDecorator(taskDecorator.getIfUnique());
        return builder;
    }
}
