package blossom.project.towelove.loves.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/6 18:36
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MyEvent类
 * 1：自定义事件类型 可以理解为只是一个标志
 * 这个事件不局限于服务的启动，可以在任何时候触发
 */

public class MyEvent extends ApplicationEvent {
    public MyEvent(Object source) {
        super(source);
    }
}
