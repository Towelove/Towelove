package blossom.project.towelove.framework.dtf.core;

import java.util.concurrent.ThreadFactory;

/**
 * @author: 张锦标
 * @date: 2023/3/16 10:06
 * NamedThreadFactory类
 * 自定义线程池名称
 *
 */
public class  NamedThreadFactory implements ThreadFactory {
    private String threadName;
    private ThreadFactory factory;
    public NamedThreadFactory(){

    }
    public NamedThreadFactory(String threadName){
        this.threadName=threadName;
    }

    public NamedThreadFactory(String threadName, ThreadFactory factory) {
        this.threadName = threadName;
        this.factory = factory;

    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        if (factory != null) {
            return factory.newThread(r);
        }
        t.setName(this.threadName);
        return t;
    }
}
