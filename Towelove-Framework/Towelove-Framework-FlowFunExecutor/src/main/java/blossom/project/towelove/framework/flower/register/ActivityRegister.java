package blossom.project.towelove.framework.flower.register;

import blossom.project.towelove.framework.flower.annotation.Activity;
import blossom.project.towelove.framework.flower.model.IActivity;
import blossom.project.towelove.framework.flower.model.IBatchActivity;
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

public class ActivityRegister implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(ActivityRegister.class);
    private ApplicationContext applicationContext;
    public static final Map<String, IActivity> ACTIVITY_MAP = new ConcurrentHashMap();
    private AtomicBoolean isRegisted = new AtomicBoolean(false);

    public ActivityRegister() {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.isRegisted.compareAndSet(false, true)) {
            Map<String, Object> map = this.applicationContext.getBeansWithAnnotation(Activity.class);
            map.forEach((beanName, bean) -> {
                if (!(bean instanceof IActivity)) {
                    throw new RuntimeException(bean.getClass().getName() + "没有继承" + IActivity.class.getName());
                } else {
                    IActivity iActivity = (IActivity)bean;
                    Activity activity = (Activity)this.applicationContext.findAnnotationOnBean(beanName, Activity.class);

                    assert activity != null;

                    String code = StringUtils.isBlank(activity.code()) ? beanName : activity.code();
                    if (ACTIVITY_MAP.putIfAbsent(code, iActivity) != null) {
                        throw new RuntimeException("业务活动组件名重复定义:" + activity);
                    }
                }
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static List<IActivity> buildActivityInstance(List<String> activities) {
        List<IActivity> list = new ArrayList();
        if (CollectionUtils.isEmpty(activities)) {
            return list;
        } else {
            activities.forEach((activityName) -> {
                IActivity activity = (IActivity)ACTIVITY_MAP.get(activityName);
                if (activity == null) {
                    throw new RuntimeException("Activity不存在，activityName = " + activityName);
                } else {
                    list.add(activity);
                }
            });
            return list;
        }
    }

    public static List<IBatchActivity> buildBatchActivityInstance(List<String> activities) {
        List<IBatchActivity> list = new ArrayList();
        if (CollectionUtils.isEmpty(activities)) {
            return list;
        } else {
            activities.forEach((activityName) -> {
                IActivity activity = (IActivity)ACTIVITY_MAP.get(activityName);
                if (activity == null) {
                    throw new RuntimeException("Activity不存在，activityName = " + activityName);
                } else if (!(activity instanceof IBatchActivity)) {
                    throw new RuntimeException("Activity不支持批处理，activityName = " + activityName);
                } else {
                    list.add((IBatchActivity)activity);
                }
            });
            return list;
        }
    }
}
