package blossom.project.towelove.framework.log.aspect;

import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.framework.log.client.LoveLogClient;
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
import java.util.Optional;

/**
 * @author jinbiao.zhang
 * 日志切面工具
 */
@Aspect
@AutoConfiguration
public class LoveLogAspect {

    @Autowired
    private LoveLogClient loveLogClient;

    //注意这里@within和@annotation的区别
    @Around("@within(blossom.project.towelove.framework.log.annotation.LoveLog) || " +
            "@annotation(blossom.project.towelove.framework.log.annotation.LoveLog)")
    public Object printMLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = SystemClock.now();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger log = LoggerFactory.getLogger(methodSignature.getDeclaringType());
        String beginTime = DateUtil.now();
        Object result = null;
        String requestId = "";
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //使用MDC设定全局请求id
        requestId = servletRequestAttributes.getRequest().getHeader("request_id");
        Result r = new Result();
        MDC.put("request_id", requestId);
        try {
            //执行原有方法
            result = joinPoint.proceed();
        } catch (Exception e){
            r = Result.fail(e,500,e.getMessage(),requestId);
            throw e;
        }finally {
            Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(),
                    methodSignature.getMethod().getParameterTypes());
            LoveLog logAnnotation =
                    Optional.ofNullable(targetMethod.getAnnotation(LoveLog.class)).orElse(joinPoint.getTarget().getClass().getAnnotation(LoveLog.class));
            if (logAnnotation != null) {
                MLogPrint logPrint = new MLogPrint();
                logPrint.setBeginTime(beginTime);
                if (logAnnotation.input()) {
                    logPrint.setInputParams(buildInput(joinPoint));
                }
                if (logAnnotation.output()) {
                    logPrint.setOutputParams(result);
                }
                String methodType = "", requestURI = "";
                try {
                    methodType = servletRequestAttributes.getRequest().getMethod();
                    requestURI = servletRequestAttributes.getRequest().getRequestURI();
                } catch (Exception ignored) {
                }
                log.info("methodType: [{}], requestURI: {},  " +
                                "executeTime: {}ms, info: {}, requestId: {}",
                        methodType,
                        requestURI,
                        SystemClock.now() - startTime,
                        JSON.toJSONString(logPrint),
                        requestId);
                r.setData(logPrint.getInputParams());
                if(200!=r.getCode()){
                    //如果出现异常就发送消息 不出现可以考虑不发送
                    loveLogClient.error(requestId,JSONObject.toJSONString(r));
                }else{
                    //loveLogClient.info(requestId,JSONObject.toJSONString(r));
                }
            }
        }
        return result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result<T> implements Serializable
    {
        private static final long serialVersionUID = 1L;

        /** 成功 */
        public static final int SUCCESS = 200;

        /** 失败 */
        public static final int FAIL = 500;

        private int code = 200;

        private String msg;

        private T data;

        private String requestId;


        public static <T> Result<T> ok(T data, String msg,String requestId)
        {
            return restResult(data, SUCCESS, msg,requestId);
        }


        public static <T> Result<T> fail(T data,int code, String msg,String requestId)
        {
            return restResult(data, code, msg,requestId);
        }


        private static <T> Result<T> restResult(T data, int code, String msg,String requestId)
        {
            Result<T> apiResult = new Result<>();
            apiResult.setCode(code);
            apiResult.setData(data);
            apiResult.setMsg(msg);
            apiResult.setRequestId(requestId);
            return apiResult;
        }
    }

    private Object[] buildInput(ProceedingJoinPoint joinPoint) {
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
            } else {
                printArgs[i] = args[i];
            }
        }
        return printArgs;
    }

    @Data
    private class MLogPrint {

        private String beginTime;

        private Object[] inputParams;

        private Object outputParams;
    }
}
