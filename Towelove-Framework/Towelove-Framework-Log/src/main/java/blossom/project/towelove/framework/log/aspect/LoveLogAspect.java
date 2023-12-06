package blossom.project.towelove.framework.log.aspect;

import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.framework.log.client.LoveLogClient;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author ZhangBlossom
 * 日志切面工具
 */
@Aspect
@AutoConfiguration
public class LoveLogAspect {

    @Autowired
    private LoveLogClient loveLogClient;

    /**
     * 注意这里@within和@annotation的区别
     * @within 用于匹配指定注解类型的所有类
     * @annotation 用于匹配指定注解类型的方法
     * 这样子当前的注解就可以使用在方法/类上
     */
    @Around("@within(blossom.project.towelove.framework.log.annotation.LoveLog) || "
            + "@annotation(blossom.project.towelove.framework.log.annotation.LoveLog)")
    public Object printMLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //请求开始时间
        long startTime = SystemClock.now();
        //请求签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger log = LoggerFactory.getLogger(methodSignature.getDeclaringType());
        String beginTime = DateUtil.now();
        Object result = null;
        String requestId = "";
        //获取请求上下文信息
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //TODO 要求前端发送请求的时候带上一个request_id
        //使用MDC设定全局请求id
        requestId = servletRequestAttributes.getRequest().getHeader(SecurityConstant.REQUEST_ID);
        Result r = new Result();
        MDC.put(SecurityConstant.REQUEST_ID, requestId);
        try {
            //执行原有方法
            result = joinPoint.proceed();
        } catch (Exception e) {
            r = Result.fail(e, 500, e.getMessage(), requestId);
            //这里throw出去的异常 会在finally执行完毕之后由全局异常处理器执行
            throw e;
        } finally {
            //获取目标方法或类上的注解
            Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(),
                    methodSignature.getMethod().getParameterTypes());
            LoveLog logAnnotation =
                    Optional.ofNullable(targetMethod.getAnnotation(LoveLog.class))
                            .orElse(joinPoint.getTarget().getClass().getAnnotation(LoveLog.class));

            if (logAnnotation != null) {
                //开始设定要打印的请求信息
                LoveLogPrint logPrint = new LoveLogPrint();
                logPrint.setBeginTime(beginTime);
                if (logAnnotation.req()) {
                    logPrint.setReqParams(buildReqParams(joinPoint));
                }
                if (logAnnotation.resp()) {
                    logPrint.setRespParams(result);
                }
                String methodType = "", requestURI = "";
                try {
                    methodType = servletRequestAttributes.getRequest().getMethod();
                    requestURI = servletRequestAttributes.getRequest().getRequestURI();
                } catch (Exception ignored) {
                }
                log.info("methodType: [{}], requestURI: {},  " + "executeTime: {}ms, info: {}, requestId: {}",
                        methodType, requestURI, SystemClock.now() - startTime, JSON.toJSONString(logPrint), requestId);
                if (200 != r.getCode()) {
                    //如果出现异常就发送消息 不出现可以考虑不发送
                    //WARN 如果再finally中进行了return 那么全局异常处理器就不会执行
                    loveLogClient.error(requestId, JSONObject.toJSONString(r));
                } else {
                    loveLogClient.info(requestId,JSONObject.toJSONString(r));
                }
            }
        }
        return result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result<T> implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 成功
         */
        public static final int SUCCESS = 200;

        /**
         * 失败
         */
        public static final int FAIL = 500;

        private int code = 200;

        private String msg;

        private T data;

        private String requestId;


        public static <T> Result<T> ok(T data, String msg, String requestId) {
            return restResult(data, SUCCESS, msg, requestId);
        }


        public static <T> Result<T> fail(T data, int code, String msg, String requestId) {
            return restResult(data, code, msg, requestId);
        }


        private static <T> Result<T> restResult(T data, int code, String msg, String requestId) {
            Result<T> apiResult = new Result<>();
            apiResult.setCode(code);
            apiResult.setData(data);
            apiResult.setMsg(msg);
            apiResult.setRequestId(requestId);
            return apiResult;
        }
    }

    /**
     * 当前方法用于构造请求参数数组
     *
     * @param joinPoint
     * @return
     */
    private Object[] buildReqParams(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object[] printArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if ((args[i] instanceof HttpServletRequest) || args[i] instanceof HttpServletResponse) {
                continue;
            }
            if (args[i] instanceof byte[]) {
                printArgs[i] = "byte array";
            } else if (args[i] instanceof MultipartFile) {
                printArgs[i] = "file";
            } else if (args[i] instanceof List<?>) {
                if (CollectionUtil.isEmpty((Collection<?>) args[i])) {
                    printArgs[i] = "empty list";
                    continue;
                }
                if (((List<?>) args[i]).get(0) instanceof MultipartFile) {
                    printArgs[i] = "files";
                } else {
                    printArgs[i] = args[i];
                }
            } else {
                printArgs[i] = args[i];
            }
        }
        return printArgs;
    }

    @Data
    private class LoveLogPrint {

        private String beginTime;

        private Object[] reqParams;

        private Object respParams;
    }
}
