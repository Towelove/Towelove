package blossom.project.towelove.society.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collections;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/5 14:19
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MyImportSelector类
 */

public class MyImportSelector implements DeferredImportSelector {
    public MyImportSelector() {
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        System.out.println("the selectImports function was be called!!!");
        return new String[]{"blossom.project.towelove.society.controller.SocietyController"};
    }

    @Override
    public Class<? extends Group> getImportGroup() {
        System.out.println("the getImportGroup function was be called!!!");
        return MyGroup.class;
    }


    public static class MyGroup implements Group {
        public MyGroup() {
        }

        @Override
        public void process(AnnotationMetadata metadata, DeferredImportSelector selector) {
            System.out.println("the process function was be called!!!");
        }

        @Override
        public Iterable<Entry> selectImports() {
            System.out.println("the selectImports function was be called!!!");
            // 返回一个空的 Iterable，而不是 null
            return Collections.emptyList();
        }

    }
}
