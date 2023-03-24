package com.towelove.common.async.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
/**
 * @author: 张锦标
 * @date: 2023/3/24 10:34
 * 全局余部任务异常处理器
 * 注意，AsyncUncaughtExceptionHandler
 * 只能拦截返回类型非 Future 的异步调用方法。
 * AsyncExecutionAspectSupport#handleError(Throwable ex, Method method, Object... params)
 * 的源码，可以很容易得到这个结论
 * 所以返回类型为 Future 的异步调用方法，
 * 需要通过 [异步回调] 来处理。
 * 并且这个方法只能对直接拥有@Async的方法扔出的报错能进行处理
 * 而再方法中调用了包含@Async的方法是不能的
 */
@AutoConfiguration
public class GlobalAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        logger.error("[handleUncaughtException][method({}) params({}) 发生异常]",
                method, params, ex);
    }

}
