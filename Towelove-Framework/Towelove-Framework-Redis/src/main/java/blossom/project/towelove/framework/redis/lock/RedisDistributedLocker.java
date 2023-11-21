package blossom.project.towelove.framework.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Redis并发锁工具类。
 * @author jinbiao.zhang
 * @version 2023/9/16 4:41 PM
 */
@Component
public class RedisDistributedLocker extends AbstractDistributedLocker {
    @Autowired
    private RedisDistributedLockFactory factory;

    @Override
    public AbstractDistributedLockFactory getDistributedLockFactory() {
        return factory;
    }
}


