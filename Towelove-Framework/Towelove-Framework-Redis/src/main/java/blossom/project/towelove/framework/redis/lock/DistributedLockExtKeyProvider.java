package blossom.project.towelove.framework.redis.lock;

/**
 * 能提供分布式锁副key的类需要继承此接口。
 * @author 张锦标
 */
public interface DistributedLockExtKeyProvider {
    String getExtKey();
}
