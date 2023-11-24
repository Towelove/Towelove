package blossom.project.towelove.framework.oss.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/24 17:39
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * IOThreadPoolConfig类
 */
@AutoConfiguration
public class ThreadPoolConfig {

    /**
     * io密集型线程池
     * @return
     */
    @Bean(name = "ioThreadPoolExecutor")
    public ThreadPoolExecutor ioIntensiveThreadPoolExecutor() {
        // 获取CPU核心数
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        // 可以根据实际情况调整
        int maximumPoolSize = corePoolSize * 2;
        // 空闲线程的存活时间
        long keepAliveTime = 60;
        TimeUnit unit = TimeUnit.SECONDS;
        //合理配置工作队列大小，过大过小都不合适
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(256);

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

    }

    /**
     * cpu密集型线程池
     * @return
     */
    @Bean(name = "cpuThreadPoolExecutor")
    public  ThreadPoolExecutor cpuIntensiveThreadPool() {
        int corePoolSize = Runtime.getRuntime().availableProcessors(); // 等于处理器数量
        int maximumPoolSize = corePoolSize; // 对于CPU密集型任务，最大线程数通常设置为与核心线程数相同
        long keepAliveTime = 0; // 当线程数大于核心线程数时，这个设置才有意义
        TimeUnit unit = TimeUnit.SECONDS;
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}
