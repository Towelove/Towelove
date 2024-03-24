package blossom.project.towelove.framework.rateLimit.aspect;

import blossom.project.towelove.framework.rateLimit.annotation.LoveRateLimiter;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.framework.rateLimit.aspect
 * @className: RateLimitAspect
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/24 15:32
 * @version: 1.0
 */
@Aspect
public class RateLimitAspect implements Order {
    private final ConcurrentHashMap<String, RateLimiter> rateLimiterCache = new ConcurrentHashMap<>();
    @Pointcut("@annotation(blossom.project.towelove.framework.rateLimit.annotation.LoveRateLimiter)")
    public void pointCut(){};

    @Around("pointCut()")
    public Object rateLimitNotice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        LoveRateLimiter loveRateLimiter = method.getAnnotation(LoveRateLimiter.class);
        //启动限流
        if (Objects.nonNull(loveRateLimiter) && loveRateLimiter.enable()){
            int qps = loveRateLimiter.qps();
            //构建限流器缓存key
            String key = getCacheKey(method,qps);
            RateLimiter rateLimiter = rateLimiterCache.computeIfAbsent(key, k -> RateLimiter.create(qps));
            if (!rateLimiter.tryAcquire()){
                throw new RuntimeException("您点击的太快了");
            }
        }
        return proceedingJoinPoint.proceed();
    }

    private String getCacheKey(Method method, int qps) {
        String clazzName = method.getDeclaringClass().getName();
        String methodName = method.getName();
        return String.format("%s#%s#%s",clazzName,methodName,qps);
    }

    @Override
    public int value() {
        return -1;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
