package blossom.project.towelove.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Application context holder.
 * 提供方便的容器类管理
 */

@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    
    private static ApplicationContext CONTEXT;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.CONTEXT = applicationContext;
    }
    
    /**
     * Get ioc container bean by type.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }
    
    /**
     * Get ioc container bean by name and type.
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return CONTEXT.getBean(name, clazz);
    }
    
    /**
     * Get a set of ioc container beans by type.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return CONTEXT.getBeansOfType(clazz);
    }
    
    /**
     * Find whether the bean has annotations.
     *
     * @param beanName
     * @param annotationType
     * @param <A>
     * @return
     */
    public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
        return CONTEXT.findAnnotationOnBean(beanName, annotationType);
    }
    
    /**
     * Get ApplicationContext.
     *
     * @return
     */
    public static ApplicationContext getContext() {
        return CONTEXT;
    }
}
