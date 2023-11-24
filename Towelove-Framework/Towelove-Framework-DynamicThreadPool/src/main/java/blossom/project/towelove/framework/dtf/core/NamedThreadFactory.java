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
    public NamedThreadFactory(){

    }
    public NamedThreadFactory(String threadName){
        this.threadName=threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(this.threadName);
        return t;
    }
}
