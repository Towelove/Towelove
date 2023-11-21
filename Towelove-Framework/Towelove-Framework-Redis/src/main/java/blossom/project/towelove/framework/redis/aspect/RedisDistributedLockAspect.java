package blossom.project.towelove.framework.redis.aspect;


import blossom.project.towelove.framework.redis.annotation.Dlock;
import blossom.project.towelove.framework.redis.annotation.DlockKey;
import blossom.project.towelove.framework.redis.lock.DistributedLockExtKeyProvider;
import blossom.project.towelove.framework.redis.lock.Lock;
import blossom.project.towelove.framework.redis.lock.LockException;
import blossom.project.towelove.framework.redis.lock.RedisDistributedLockFactory;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author: 张锦标
 * @date: 2023/8/22 14:29
 * RedisDistributedLockAspect类
 * 当前类用于提供Redis分布式锁
 */
@Component
@Aspect
@Slf4j
@Order(Integer.MAX_VALUE - 1)
@AutoConfiguration
public class RedisDistributedLockAspect {
    @Autowired
    private RedisDistributedLockFactory lockFactory;


    @Around("@annotation(blossom.project.towelove.framework.redis.annotation.Dlock)")
    public Object lock(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //判断当前方法是否由@Dlock注解进行注释
        if (!method.isAnnotationPresent(Dlock.class)) {
            log.error("should not be here when method was not annotated by @Dlock");
            return proceedingJoinPoint.proceed();
        }
        Dlock annotation = method.getAnnotation(Dlock.class);
        //获得主key
        final String mainKey = annotation.value();
        checkState(StringUtils.isNotBlank(mainKey), "Annotation @Dlock required a non-block value");
        //获取副key
        StringBuilder extKey = new StringBuilder();
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof DistributedLockExtKeyProvider) {
                extKey = new StringBuilder(((DistributedLockExtKeyProvider) arg).getExtKey()); break;
            }
        }
        //提取@DlockKey注解过的参数
        Parameter[] parameters = method.getParameters();

        //参数序号
        int index = 0;
        List<String> paramKeys = Lists.newArrayList();
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(DlockKey.class)) {
                paramKeys.add(args[index].toString());
            }
            index++;
        }

        if (!CollectionUtils.isEmpty(paramKeys)) {

            //各个参数是否需要排序
            if (annotation.sort()) {
                Collections.sort(paramKeys);
            }

            //拼装extKey
            for (String paramKey : paramKeys) {
                extKey = extKey.append("_").append(paramKey);
            }
        }

        final String key;
        if (StringUtils.isNotBlank(extKey.toString())) {
            key = mainKey + "_" + extKey;
        } else {
            key = mainKey;
        }

        Lock lock = lockFactory.getDistributedLock(key);
        Preconditions.checkArgument(lock != null, "Obtain distributed lock failed.");

        try {
            if (lock.lock()) {
                log.info("Obtain distributed lock for key:{}.", key);
                return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            } else {
                log.error("Distributed lock failed to lock for key:{}.", key);
                throw new IllegalStateException("Distributed lock failed to lock for key:" + key + ".");
            }
        } catch (LockException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Distributed lock error.", e);
        } finally {
            lock.unlock();
        }
    }
}
