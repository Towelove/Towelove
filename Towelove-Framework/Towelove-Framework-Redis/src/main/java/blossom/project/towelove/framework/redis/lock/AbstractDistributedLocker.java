package blossom.project.towelove.framework.redis.lock;

/**
 * 并发锁的加锁与解锁操作抽象类。
 * @author 张锦标
 * @version 1.0
 */
public abstract class AbstractDistributedLocker {

    public abstract AbstractDistributedLockFactory getDistributedLockFactory();

    /**
     * 加锁。
     * @param key
     * @return
     */
    public boolean lock(String key) throws Exception {
        try {
            Lock lock = getDistributedLockFactory().getDistributedLock(key);
            if (lock == null) {
                return false;
            }

            return lock.lock();
        } catch (Throwable e) {
            throw new Exception("Can not obtain distributed lock.");
        }
    }

    /**
     * 解锁。
     * @param key
     */
    public void unlock(String key) throws Exception {
        unlock(getDistributedLockFactory().getDistributedLock(key));
    }

    /**
     * 解锁。
     * @param lock
     */
    private void unlock(Lock lock) throws Exception {
        if (lock == null) {
            return;
        }

        try {
            lock.unlock();
        }catch (Throwable e){
            throw new Exception("Can not release distributed lock.");
        }
    }
}


