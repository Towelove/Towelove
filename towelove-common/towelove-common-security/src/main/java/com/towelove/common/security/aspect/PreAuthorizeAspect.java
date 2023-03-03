package com.towelove.common.security.aspect;

import com.towelove.common.security.annotation.RequiresLogin;
import com.towelove.common.security.annotation.RequiresPermissions;
import com.towelove.common.security.annotation.RequiresRoles;
import com.towelove.common.security.auth.AuthUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 基于 Spring Aop 的注解鉴权
 * 内部服务调用验证处理
 *
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
@Aspect
@Component
public class PreAuthorizeAspect
{
    /**
     * 构建
     */
    public PreAuthorizeAspect()
    {
    }

    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " " +
            "@annotation(com.towelove.common.security.annotation.RequiresLogin) || "
            + "@annotation(com.towelove.common.security.annotation.RequiresPermissions) || "
            + "@annotation(com.towelove.common.security.annotation.RequiresRoles)";

    /**
     * 声明AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut()
    {
    }

    /**
     * 环绕切入
     * 
     * @param joinPoint 切面对象，用来获取代理类和被代理类的信息。
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable
    {
        // 注解鉴权
        //MethodSignature代表的即是被切入的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        checkMethodAnnotation(signature.getMethod());
        System.out.println("当前被切入的方法为："+signature.getMethod().getName().toString());
        try
        {
            // 执行原有逻辑
            Object obj = joinPoint.proceed();
            return obj;
        }
        catch (Throwable e)
        {
            throw e;
        }
    }

    /**
     * 对一个Method对象进行注解检查
     */
    public void checkMethodAnnotation(Method method)
    {
        // 校验 @RequiresLogin 注解
        RequiresLogin requiresLogin = method.getAnnotation(RequiresLogin.class);
        if (requiresLogin != null)
        {
            AuthUtil.checkLogin();
        }

        // 校验 @RequiresRoles 注解
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        if (requiresRoles != null)
        {
            AuthUtil.checkRole(requiresRoles);
        }

        // 校验 @RequiresPermissions 注解
        RequiresPermissions requiresPermissions =
                method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null)
        {
            AuthUtil.checkPermi(requiresPermissions);
        }
    }
}
