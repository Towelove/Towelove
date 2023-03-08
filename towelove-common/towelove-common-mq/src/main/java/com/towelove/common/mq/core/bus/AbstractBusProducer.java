package com.towelove.common.mq.core.bus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

/**
 * 基于 Spring Cloud Bus 实现的 Producer 抽象类
 *
 * @author 张锦标
 *
 */
public abstract class AbstractBusProducer {
    /**
     * 通过当前类可以完成事件的发布
     * 底层使用的就是ApplicationEvent，
     * 继承这个类就可以实现自定义事件
     * 使用其source属性可以获取事件源以及timestamp属性可以获取
     * 事件发生的时间
     * 可以使用ApplicationListener完成事件的监听
     */
    @Resource
    protected ApplicationEventPublisher applicationEventPublisher;

    @Resource
    protected ServiceMatcher serviceMatcher;

    @Value("{spring.application.name}")
    protected String applicationName;

    protected void publishEvent(RemoteApplicationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * @return 只广播给自己服务的实例
     */
    protected String selfDestinationService() {
        String destination = applicationName + ":**";
        return destination;
    }

    protected String getBusId() {
        String busId = serviceMatcher.getBusId();
        return busId;
    }
    protected String getMonitorBusId(){
        //TODO 使用注册中心的方式获取所有的实例
        //然后获取监控中心的实例名称
        return "towelove-monitor-mq:7777";
    }
    protected String sendToMonitorMqDestination(){
        return "**:**";
    }

}
