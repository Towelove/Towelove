package com.towelove.monitor.test.thread;

import java.time.LocalTime;

/**
 * @author: 张锦标
 * @date: 2023/5/6 10:43
 * DeamonTest类
 * 使用守护线程去杀死定时任务的线程
 */
public class DeamonTest {
    public static void main(String[] args) {
        TimerThread t = new TimerThread();
        t.start();
        DaemonThread d = new DaemonThread(t);
        d.setDaemon(true);
        d.start();
    }
}

class DaemonThread extends Thread {
    TimerThread t;
    public DaemonThread(TimerThread t){
        this.t = t;
    }
    @Override
    public void run() {
        System.out.println("杀死线程Timer");
        t.interrupt();
    }
}

class TimerThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println(LocalTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
