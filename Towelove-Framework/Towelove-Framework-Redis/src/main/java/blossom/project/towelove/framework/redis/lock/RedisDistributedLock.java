package blossom.project.towelove.framework.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的分布式锁。
 */
public class RedisDistributedLock implements Lock {
    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);
    private static final int DEFAULT_ACQUIRE_RESOLUTION_MILLIS = 100;
    private static final String KEY_PREFIX = "kairos-lock-";

    // redis key
    private final String key;
    private final StringRedisTemplate redisTemplate;
    // 锁超时时间
    private final long lockExpiredTimeMS;
    // 获取锁定等待时间
    private final long lockWaitingTimeMS;

    private final ThreadLocal<Boolean> locked = new ThreadLocal<>();

    public RedisDistributedLock(String key, StringRedisTemplate redisTemplate, long lockExpiredTimeMS, long lockWaitingTimeMS) {
        this.key = KEY_PREFIX + key;
        this.redisTemplate = redisTemplate;
        this.lockExpiredTimeMS = lockExpiredTimeMS;
        this.lockWaitingTimeMS = lockWaitingTimeMS;
    }

    @Override
    public boolean lock() throws LockException, InterruptedException {
        locked.set(false);
        long timeout = lockWaitingTimeMS;
        while (timeout > 0) {
            final String writtenValue = String.valueOf(System.currentTimeMillis() + this.lockExpiredTimeMS + 1);
            boolean success = redisTemplate.opsForValue().setIfAbsent(key, writtenValue);
            if (success) {
                locked.set(true);
                return true;
            }
            final String storedValue = redisTemplate.opsForValue().get(key);
            logger.warn("[debug] get lock. key:{}, value: {}", key, storedValue);
            if (storedValue == null) {
                // 应对进入循环没拿到锁，但是get之前锁已被其他线程释放导致storedValue为null的情况；
                // 立即尝试重新获取锁
                continue;
            }
            long existedLockExpireTime;
            try {
                existedLockExpireTime = Long.parseLong(storedValue);
            } catch (NumberFormatException e) {
                logger.error("Distributed lock stored invalid value:{}", storedValue);
                throw new LockException("Distributed lock stored invalid value:" + storedValue, e);
            }

            // 如果redis中的锁过期了
            if (existedLockExpireTime < System.currentTimeMillis()) {
                String retValue = redisTemplate.opsForValue().getAndSet(key, writtenValue);
                if (!StringUtils.isEmpty(retValue) && retValue.equalsIgnoreCase(storedValue)) {
                    locked.set(true);
                    return true;
                }
            }

            timeout -= DEFAULT_ACQUIRE_RESOLUTION_MILLIS;

            logger.info("Try lock failed, waiting...");
            // 延迟100毫秒, 这里使用随机时间可能会好一点,可以防止饥饿进程的出现
            TimeUnit.MILLISECONDS.sleep(DEFAULT_ACQUIRE_RESOLUTION_MILLIS);
        }
        return false;
    }

    @Override
    public void unlock() {
        if (locked.get()) {
            redisTemplate.delete(key);
            locked.set(false);
            locked.remove();
            logger.info("Release lock for key: {}", key);
        }
    }
}
