package com.towelove.monitor.test.thread;

import org.apache.tomcat.util.digester.ArrayStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author: 张锦标
 * @date: 2023/5/6 16:56
 * CompletableFuture类
 * 串行的使用CF
 * 也就是让第一个任务执行完毕之后作为第二个任务的内容
 */
public class CompletableFutureTest1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenApplyAsync(e -> e - 1)
                .whenComplete((v,e)->{
                    System.out.println("value:"+v);
                });

        cf.join();
        System.out.println(cf.get());


        /**
         * CompletableFuture 的任务是串联的，如果它的其中某一步骤发生了异常，
         * 会影响后续代码的运行的。
         *
         * exceptionally 从名字就可以看出，是专门处理这种情况的。
         * 比如，我们强制某个步骤除以 0，发生异常，捕获后返回 -1，它将能够继续运行。
         */
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(e->e/0)
                .thenApplyAsync(e -> e - 1)
                .exceptionally(ex->{
                    System.out.println(ex);
                    return -1;
                });

        cf1.join();
        System.out.println(cf1.get());

        System.out.println("----------------------------");

        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Object> requests = new ArrayList<>();
        requests.add(123);
        requests.add(1553);
        requests.add(5435);
        requests.add("As");

        CountDownLatch countDown = new CountDownLatch(requests.size());
        for(Object request:requests){
            executor.execute(()->{
                try{
                    System.out.println(request);
                }finally{
                    countDown.countDown();
                }
            });
        }
        countDown.await(200,TimeUnit.MILLISECONDS);


        System.out.println("----------------------------");


        List<CompletableFuture<Object>> futureList = requests
                .stream()
                .map(request->
                        CompletableFuture.supplyAsync(()->{
                            System.out.println(request);
                            return new Object();
                        }, executor))

                .collect(Collectors.toList());

        CompletableFuture<Void> allCF = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        
        allCF.join();
    }
}

