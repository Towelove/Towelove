package blossom.project.towelove.framework.rocketmq.iface;

/**
 * @author: 张锦标
 * @date: 2024/4/18 5:04 PM
 * MessageListener接口
 */
public interface MessageListener {
    public Object consume(Object message);
}
