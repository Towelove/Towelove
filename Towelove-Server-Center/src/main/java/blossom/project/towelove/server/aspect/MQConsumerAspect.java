package blossom.project.towelove.server.aspect;


import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.framework.log.LoveLogContext;
import blossom.project.towelove.framework.log.LoveLogThreadLocal;
import blossom.project.towelove.framework.redis.service.RedisService;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2024/4/18 5:21 PM
 * 消息消费者拦截器
 */
//@Aspect
//@Component
public class MQConsumerAspect {
    /**
     * 接口耗时超5s打错误日志
     */
    private static final long errorCost = 5000;
    /**
     * 接口耗时超1s打警告日志
     */
    private static final long warnCost =  1000;

    //@Autowired
    private RedisService redisService;

    @Around("execution(public * blossom.project.towelove.server.mq.rocketmq..*.onMessage(..))")
    public Object execute(ProceedingJoinPoint pjp) throws  Throwable {
        Object[] args = pjp.getArgs();
        Object target = pjp.getTarget();
        Class clazz = target.getClass();
        String className = clazz.getSimpleName();
        MessageExt msg = (MessageExt)args[0];
        // 幂等处理
        String idempotentKey = String.format("%s:%s",className , msg.getMsgId());
        redisService.setCacheObject(idempotentKey,1);
        Object o = redisService.getCacheObject(idempotentKey);
        try {
            setLogTitleAndTag(msg, null);
            if(o == null){
                return pjp.proceed(args);
            }
            redisService.setCacheObject(idempotentKey, "1", 30l, TimeUnit.MINUTES);
            o = redisService.getCacheObject(idempotentKey);
            //if (StringUtils.isBlank(o)) {
                //TOdo 消息重复消费
                //日志 埋点
            //}

            long startTime = System.currentTimeMillis();
            Object rst = pjp.proceed(args);
            long endTime = System.currentTimeMillis();
            long spend = endTime - startTime;
            if(spend > warnCost && spend < errorCost) {
                //日志
            } else if(spend > errorCost){
                //日志
            }
            if (rst instanceof Object) {
                // 如果返回非消费成功, 删除幂等的redisKey
               //todo 这里需要搞一个方法 能拿到mq的消息返回状态
                //因为我们知道mq消息消费成功的时候应该是有返回值的
                //我得用这个返回值告诉你你不要再消费了
            } else {
            }
            return rst;
        } catch (Exception e) {
            //todo 埋点操作用于监控
            //todo 日志操作
            // 幂等处理
            if(idempotentKey != null) {
                redisService.deleteObject(idempotentKey);
            }
            //mq 消息消费失败
        } finally {
            LoveLogThreadLocal.clear();
        }
        return "ok";
    }

    /**
     * 设置日志的title和tag等信息
     * @param msg
     * @param consumer
     */
    public void setLogTitleAndTag(MessageExt msg, MessageListener consumer) {
        //LogContext初始化，并绑定到InheritableThreadLocal
        LoveLogContext loveLogContext = LoveLogThreadLocal.init();
        if (StringUtils.isNotBlank(msg.getMsgId())) {
            loveLogContext.addTag("MESSAGE_ID", msg.getMsgId());
        }
        //日志设置title
        loveLogContext.setTitle(consumer.getClass().getSimpleName());
        //todo 后续存储分析操作
    }

}
