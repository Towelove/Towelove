package blossom.project.towelove.loves.listener;

import blossom.project.towelove.loves.event.MyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/6 18:37
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MyListener类
 * 1：自定义事件监听器
 */
@Component
public class MyListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("--------自定义事件触发----------");

        System.out.println("--------自定义事件触发----------");
    }
}
