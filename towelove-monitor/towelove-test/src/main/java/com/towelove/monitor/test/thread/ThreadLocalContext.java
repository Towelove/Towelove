package com.towelove.monitor.test.thread;

/**
 * @author: 张锦标
 * @date: 2023/5/7 12:21
 * ThreadLocalContext类
 */
public class ThreadLocalContext implements AutoCloseable{
    private static final ThreadLocal<String> tl = new ThreadLocal<>();
    public ThreadLocalContext(){

    }
    public ThreadLocalContext(String str){
        tl.set(str);
    }

    public static String get(){
        return tl.get();
    }


    @Override
    public void close() throws Exception {
        System.out.println("移除了tl");
        System.out.println("当前TLC中的数据为"+ThreadLocalContext.get());
        tl.remove();
    }

    public static void main(String[] args) {
        //1:放入资源
        ThreadLocalContext tlc = new ThreadLocalContext("123213");
        System.out.println("do something....");
        //2:使用资源
        //这里假设在另一个地方调用
        //但是其实都是同一个线程
        useTLC();
    }

    public static void useTLC(){
        //这里看上去是new了一个新对象，但是对于TL来说无所谓
        try(ThreadLocalContext tlc = new ThreadLocalContext()){
            System.out.println("使用资源："+ThreadLocalContext.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
