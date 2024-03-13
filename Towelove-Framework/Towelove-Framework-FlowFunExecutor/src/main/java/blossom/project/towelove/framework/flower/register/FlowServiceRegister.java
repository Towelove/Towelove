package blossom.project.towelove.framework.flower.register;

import blossom.project.towelove.framework.flower.annotation.FlowService;
import blossom.project.towelove.framework.flower.model.service.FlowSerivce;
import blossom.project.towelove.framework.flower.model.service.BatchFlowService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: ZhangBlossom
 * @date: 2024/3/13 20:19
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 流水线函数执行器服务注册器
 */
public class FlowServiceRegister implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(FlowServiceRegister.class);
    private ApplicationContext applicationContext;
    public static final Map<String, FlowSerivce> FLOWSERVICE_MAP = new ConcurrentHashMap();
    private AtomicBoolean isRegisted = new AtomicBoolean(false);

    public FlowServiceRegister() {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.isRegisted.compareAndSet(false, true)) {
            Map<String, Object> map = this.applicationContext.getBeansWithAnnotation(FlowService.class);
            map.forEach((beanName, bean) -> {
                if (!(bean instanceof FlowSerivce)) {
                    throw new RuntimeException(bean.getClass().getName() + "没有继承" + FlowSerivce.class.getName());
                } else {
                    FlowSerivce flowService = (FlowSerivce)bean;
                    FlowService flowSerivce = (FlowService)this.applicationContext.findAnnotationOnBean(beanName, FlowService.class);

                    assert flowSerivce != null;

                    String code = StringUtils.isBlank(flowSerivce.code()) ? beanName : flowSerivce.code();
                    if (FLOWSERVICE_MAP.putIfAbsent(code, flowService) != null) {
                        throw new RuntimeException("业务活动组件名重复定义:" + flowSerivce);
                    }
                }
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static List<FlowSerivce> buildFlowServiceInstance(List<String> serviceList) {
        List<FlowSerivce> list = new ArrayList();
        if (CollectionUtils.isEmpty(serviceList)) {
            return list;
        } else {
            serviceList.forEach((FlowServiceName) -> {
                FlowSerivce flowSerivce = (FlowSerivce) FLOWSERVICE_MAP.get(FlowServiceName);
                if (flowSerivce == null) {
                    throw new RuntimeException("FlowService不存在，ServiceName = " + FlowServiceName);
                } else {
                    list.add(flowSerivce);
                }
            });
            return list;
        }
    }

    public static List<BatchFlowService> buildBatchFlowServiceInstance(List<String> activities) {
        List<BatchFlowService> list = new ArrayList();
        if (CollectionUtils.isEmpty(activities)) {
            return list;
        } else {
            activities.forEach((FlowServiceName) -> {
                FlowSerivce flowSerivce = (FlowSerivce) FLOWSERVICE_MAP.get(FlowServiceName);
                if (flowSerivce == null) {
                    throw new RuntimeException("FlowService不存在，FlowServiceName = " + FlowServiceName);
                } else if (!(flowSerivce instanceof BatchFlowService)) {
                    throw new RuntimeException("FlowService不支持批处理，FlowServiceName = " + FlowServiceName);
                } else {
                    list.add((BatchFlowService)flowSerivce);
                }
            });
            return list;
        }
    }
}
