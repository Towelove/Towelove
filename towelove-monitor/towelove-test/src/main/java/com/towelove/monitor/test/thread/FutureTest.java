package com.towelove.monitor.test.thread;

import java.util.concurrent.*;

/**
 * @author: 张锦标
 * @date: 2023/5/6 16:51
 * FutureTest类
 */
public class FutureTest {
    public static void main(String[] args) throws InterruptedException {
        MyFuture myFuture = new MyFuture();
        ThreadPoolExecutor t = new ScheduledThreadPoolExecutor(4);
        Future<String> submit = t.submit(myFuture);
        Thread.sleep(100);
        try {
            //必须阻塞等待获取这个任务
            if (submit.isDone()){
                System.out.println("ok");
            }
            System.out.println(submit.get(2,TimeUnit.SECONDS));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
class MyFuture implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("hello this is callable ");
        Thread.sleep(1);
        return "success";
    }
}
