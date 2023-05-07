package com.towelove.monitor.test.thread;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/5/6 16:43
 * ScheduleThreadPoolTest类
 * 定时任务的线程池有两个特点：
 * 1：如果你设定的定时任务是按照固定速率的，那么如果
 * 任务的执行超过了你设定的速率，必须等待第一个任务执行完毕之后才会
 * 开始执行下一个任务
 * 2：如果当前执行的任务出现了异常，那么后面的任务也直接不执行了，
 * 并且线程池不会停止，程序继续运行
 */
public class ScheduleThreadPoolTest {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(3);
        stpe.scheduleAtFixedRate(()->{
            System.out.println("执行一次");
            try {
                Thread.sleep(2000);
                throw new RuntimeException();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //System.out.println("heelo woasd");
        }, 1,1, TimeUnit.SECONDS );
    }
}
