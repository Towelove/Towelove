package blossom.project.towelove.framework.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by max.shi on 17/11/26.
 */
@AutoConfiguration
public class RedisDistributedLockFactory extends AbstractDistributedLockFactory {
    private static final ConcurrentHashMap<String, RedisDistributedLock> LOCKS
            = new ConcurrentHashMap<>(1024);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${lock.expire:60000}")
    private long lockExpiredTimeMS;

    @Value("${lock.waiting:10000}")
    private long lockWaitingTimeMS;

    /**
     * 获取分布式锁。
     * @param key
     * @return
     */
    @Override
    public Lock getDistributedLock(String key) {
        checkState(!StringUtils.isEmpty(key), "Required non-blank param 'key'");
        RedisDistributedLock lock = LOCKS.get(key.trim());
        if (lock == null) {
            synchronized (this) {
                lock = LOCKS.get(key.trim());
                if (lock == null) {
                    lock = new RedisDistributedLock(key, stringRedisTemplate, lockExpiredTimeMS, lockWaitingTimeMS);
                    LOCKS.putIfAbsent(key, lock);
                }
            }
        }
        return lock;
    }
}
