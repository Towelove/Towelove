package com.towelove.monitor.test.thread;

/**
 * @author: 张锦标
 * @date: 2023/5/7 16:32
 * JoinTest类
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            System.out.println("@13213");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();;
        System.out.println("123123");
    }
}
