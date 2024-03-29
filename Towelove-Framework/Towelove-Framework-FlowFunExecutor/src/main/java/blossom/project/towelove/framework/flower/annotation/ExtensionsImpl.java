package blossom.project.towelove.framework.flower.annotation;

import blossom.project.towelove.framework.flower.enums.TemplateTypeEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/12 14:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 还在开发ing
 */
@Deprecated
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ExtensionsImpl {
    String templateCode() default "";

    TemplateTypeEnum templateType();
}
