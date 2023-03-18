package com.towelove.msg.task.config;

import cn.hutool.extra.spring.SpringUtil;
import com.towelove.common.core.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.TimerTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 季台星
 * @Date 2023 03 18 11 15
 */
public class asynLogManager {

    @Autowired
    @Qualifier("logThreadPool")
    private static ThreadPoolExecutor executor;


    private asynLogManager() {
    }
    //单例模式
    public static ThreadPoolExecutor getIns(){
        return executor;
    }

    //执行任务
    public void execute(TimerTask task){
        execute(task);
    }
    //停止线程池，（等待任务全部完成）
    public void destoryThread(){
        executor.shutdown();
    }
}
