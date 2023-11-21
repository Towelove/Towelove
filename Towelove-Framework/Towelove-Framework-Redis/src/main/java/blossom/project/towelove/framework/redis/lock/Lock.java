package blossom.project.towelove.framework.redis.lock;

/**
 * @author 张锦标
 */
public interface Lock {
    boolean lock() throws LockException, InterruptedException;

    void unlock() ;
}
