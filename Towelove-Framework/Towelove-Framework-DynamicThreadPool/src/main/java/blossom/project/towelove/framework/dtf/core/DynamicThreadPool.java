package blossom.project.towelove.framework.dtf.core;


import blossom.project.towelove.framework.dtf.config.ThreadPoolProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/3/16 9:58
 * ThreadPoolConfig类
 * 参考美团的动态线程池
 */
@AutoConfiguration
public class DynamicThreadPool {
    private static final long AWAIT_TIMEOUT = 600;

    @Autowired
    private ThreadPoolProperty threadPoolProperty;

    /**
     * 初始化IO密集型线程池
     *
     * @return
     */
    @Bean("ioDynamicThreadPool")
    @Primary
    public ThreadPoolExecutor ioIntensiveThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = generateThreadPool(0);
        System.out.println(threadPoolExecutor);
        return threadPoolExecutor;
    }
    /**
     * 初始化CPU密集型线程池
     *
     * @return
     */
    @Bean("cpuDynamicThreadPool")
    public ThreadPoolExecutor cpuIntensiveThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = generateThreadPool(1);
        System.out.println(threadPoolExecutor);
        return threadPoolExecutor;
    }

    @Bean("virtualThreadThreadPool")
    public ThreadPoolExecutor virtualThreadThreadPool() {
        return generateThreadPool(2);
    }




    /**
     * 初始化线程池
     *
     * @return
     */
    private ThreadPoolExecutor generateThreadPool(int type) {
        switch (type){
            case 0:{
                return new ThreadPoolExecutor(threadPoolProperty.getIoCorePoolSize(), threadPoolProperty.getIoMaximumPoolSize(),
                        60, TimeUnit.SECONDS,
                        new ResizableCapacityLinkedBlockIngQueue<Runnable>(threadPoolProperty.getIoQueueCapacity()),
                        new NamedThreadFactory("io-thread-"), new ThreadPoolExecutor.DiscardPolicy());

            }
            case 2: {
                ThreadFactory factory = Thread.ofVirtual().factory();
                return new ThreadPoolExecutor(
                        threadPoolProperty.getVirtualCorePoolSize(), threadPoolProperty.getVirtualCorePoolSize(),
                        60, TimeUnit.SECONDS,
                        new ResizableCapacityLinkedBlockIngQueue<>(threadPoolProperty.getVirtualQueueCapacity()),
                        new NamedThreadFactory("virtual-thread-", factory),
                        new ThreadPoolExecutor.DiscardPolicy()
                );
            }
            default:{
                return new ThreadPoolExecutor(threadPoolProperty.getCpuCorePoolSize(), threadPoolProperty.getCpuMaximumPoolSize(),
                        60, TimeUnit.SECONDS,
                        new ResizableCapacityLinkedBlockIngQueue<Runnable>(threadPoolProperty.getCpuQueueCapacity()),
                        new NamedThreadFactory("cpu-thread-"), new ThreadPoolExecutor.DiscardPolicy());
            }
        }
    }


    /**
     * 为了实现优雅停机的目标，我们应当先调用shutdown方法，调用这个方法也就意味着，
     * 这个线程池不会再接收任何新的任务，但是已经提交的任务还会继续执行。之后我们还应当调用awaitTermination
     * 方法，这个方法可以设定线程池在关闭之前的最大超时时间，如果在超时时间结束之前线程池能够正常关闭则会返回true，
     * 否则，超时会返回false。通常我们需要根据业务场景预估一个合理的超时时间，
     * 然后调用该方法。如果awaitTermination方法返回false
     * ，但又希望尽可能在线程池关闭之后再做其他资源回收工作，可以考虑再调用一下shutdownNow
     * 方法，此时队列中所有尚未被处理的任务都会被丢弃，同时会设置线程池中每个线程的中断标志位。
     * shutdownNow并不保证一定可以让正在运行的线程停止工作，除非提交给线程的任务能够正确响应中断。
     *
     * @param poolExecutor
     */
    public void destroy(ThreadPoolExecutor poolExecutor) {
        try {
            poolExecutor.shutdown();
            if (!poolExecutor.awaitTermination(AWAIT_TIMEOUT, TimeUnit.SECONDS)) {
                poolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // 如果当前线程被中断，重新取消所有任务
            poolExecutor.shutdownNow();
            // 保持中断状态
            Thread.currentThread().interrupt();
        }
    }


    /**
     * 打印线程池状态
     *
     * @param executor
     */
    public static String threadPoolStatus(ThreadPoolExecutor executor) {
        BlockingQueue<Runnable> queue = executor.getQueue();
        return LocalTime.now().toString() + "  " + Thread.currentThread().getName()  + "核心线程数:" + executor.getCorePoolSize() + "活动线程数:" + executor.getActiveCount() + "最大线程数:" + executor.getMaximumPoolSize() + "线程池活跃度:" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) + "任务完成数:" + executor.getCompletedTaskCount() + "队列大小:" + (queue.size() + queue.remainingCapacity()) + "当前排队线程数:" + queue.size() + " 队列剩余大小:" + queue.remainingCapacity() + "队列使用度:" + divide(queue.size(), queue.size() + queue.remainingCapacity());
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
