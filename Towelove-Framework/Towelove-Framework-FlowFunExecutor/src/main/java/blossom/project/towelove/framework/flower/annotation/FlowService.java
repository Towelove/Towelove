package blossom.project.towelove.framework.flower.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author: ZhangBlossom
 * @date: 2024/1/23 12:20
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 * 流水线编排执行函数定义注解
 * 使用当前注解的作用是定义当前类的作用是
 * 1. 定义当前类是流水线编排执行函数
 * 2. 标注这个流水线接口的业务场景
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface FlowService {

    /**
     * 唯一标识 必填
     * @return
     */
    String code() default "";

    /**
     * 名称 非必填
     * @return
     */
    String name();

    /**
     * 描述当前类作用
     * @return
     */
    String desc() default "";
}
