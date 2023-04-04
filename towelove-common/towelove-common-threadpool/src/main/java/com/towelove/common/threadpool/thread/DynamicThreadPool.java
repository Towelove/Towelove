package com.towelove.common.threadpool.thread;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalTime;
import java.util.concurrent.*;

/**
 * @author: 张锦标
 * @date: 2023/3/16 9:58
 * ThreadPoolConfig类
 * 参考美团的动态线程池
 */
@AutoConfiguration
public class DynamicThreadPool {
    /**
     * 初始化线程池
     *
     * @return
     */
    @Bean("logThreadPool")
    private static ThreadPoolExecutor logThreadPool() {
        return new ThreadPoolExecutor(4,
                6,
                60,
                TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockIngQueue<Runnable>(10),
                new NamedThreadFactory("log-thread-"),
                new ThreadPoolExecutor.DiscardPolicy());
    }
    /**
     * 初始化线程池
     *
     * @return
     */
    @Bean("cpuThreadPool")
    @Primary
    private static ThreadPoolExecutor cpuThreadPool() {
        return new ThreadPoolExecutor(4,
                6,
                60,
                TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockIngQueue<Runnable>(10),
                new NamedThreadFactory("cpu-thread-"),
                new ThreadPoolExecutor.DiscardPolicy());
    }

    /**
     * 初始化线程池
     *
     * @return
     */
    @Bean("fileThreadPool")
    private static ThreadPoolExecutor fileThreadPool() {
        return new ThreadPoolExecutor(3,
                3,
                60,
                TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockIngQueue<Runnable>(10),
                new NamedThreadFactory("file-thread-"),
                new ThreadPoolExecutor.DiscardPolicy());
    }
    /**
     * 初始化线程池
     *
     * @return
     */
    //@Bean("fileThreadPool")
    //private static ThreadPoolExecutor buildThreadPoolExecutor() {
    //    return new ThreadPoolExecutor(3,
    //            3,
    //            60,
    //            TimeUnit.SECONDS,
    //            new ResizableCapacityLinkedBlockIngQueue<Runnable>(10),
    //            new NamedThreadFactory("file-thread-"),
    //            new ThreadPoolExecutor.DiscardPolicy());
    //}

    /**
     * 先提交任务给线程池，并修改线程池状态
     */
    private static void dynamicModifyThreadPoolExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = fileThreadPool();
        for (int i = 0; i < 15; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        //动态改变线程池状态 可以使用nacos等监控工具
        threadPoolStatus(executor, "改变之前");
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        ResizableCapacityLinkedBlockIngQueue<Runnable> queue = (ResizableCapacityLinkedBlockIngQueue<Runnable>) executor.getQueue();
        queue.setCapacity(100);
        threadPoolStatus(executor, "改变之后");
        Thread.currentThread().join();

    }

    /**
     * 打印线程池状态
     *
     * @param executor
     * @param name
     */
    private static void threadPoolStatus(ThreadPoolExecutor executor, String name) {
        BlockingQueue<Runnable> queue = executor.getQueue();
        System.out.println(LocalTime.now().toString() + "  " + Thread.currentThread().getName() + name + "核心线程数:" + executor.getCorePoolSize() + "活动线程数:" + executor.getActiveCount() + "最大线程数:" + executor.getMaximumPoolSize() + "线程池活跃度:" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) + "任务完成数:" + executor.getCompletedTaskCount() + "队列大小:" + (queue.size() + queue.remainingCapacity()) + "当前排队线程数:" + queue.size() + " 队列剩余大小:" + queue.remainingCapacity() + "队列使用度:" + divide(queue.size(), queue.size() + queue.remainingCapacity()));
    }

    /**
     * 保留两位小数
     *
     * @param num1
     * @param num2
     * @return
     */
    private static String divide(int num1, int num2) {
        return String.format("%1.2f%%", Double.parseDouble(num1 + "") / Double.parseDouble(num2 + "") * 100);
    }


}
