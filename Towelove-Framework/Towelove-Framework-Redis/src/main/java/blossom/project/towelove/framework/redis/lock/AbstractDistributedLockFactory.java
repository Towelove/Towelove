package blossom.project.towelove.framework.redis.lock;

/**
 * 分布式锁工厂。
 * @author 张锦标
 * @version 1.0
 */
public abstract class AbstractDistributedLockFactory {
    public abstract Lock getDistributedLock(String key);
}


