package com.towelove.monitor.test.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 张锦标
 * @date: 2023/5/6 10:54
 * ThreadSynchroinzed类
 */
public class ThreadSynchroinzed {
    public static void main(String[] args) throws Exception {
        AddThread add = new AddThread();
        DecThread dec = new DecThread();
        add.start();
        dec.start();
        add.join();
        dec.join();
        System.out.println(Counter.count);
    }
}

class Counter {
    //如果这里直接用int类型就会出现数据共享异常
    public static AtomicInteger count =new AtomicInteger(0);
}

class AddThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Counter.count.getAndIncrement();
        }
    }
}

class DecThread extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Counter.count.getAndDecrement();
        }
    }
}
