package blossom.project.towelove.common.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/9 22:06
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 对于go语言中解决缓存击穿工具SingleFlight的实现
 */
public abstract class SingleFlight<K, V> {
    private final ConcurrentHashMap<K, CompletableFuture<V>> ongoingOperations = new ConcurrentHashMap<>();

    /**
     * 版本号控制，使得每次进行更新的时候，一定都是对最新数据进行更新
     */
    private final ConcurrentHashMap<K, AtomicLong> versions = new ConcurrentHashMap<>();

    // 设置默认超时时间，例如3秒
    private final long defaultTimeout = 3 * 1000;


    protected abstract boolean acquireDistributedLock(K key);

    protected abstract void releaseDistributedLock(K key);

    /**
     * 更新指定键的版本号
     *
     * @param key 键
     */
    public void updateVersion(K key) {
        versions.compute(key, (k, version) -> {
            if (version == null) {
                return new AtomicLong(1);
            } else {
                version.incrementAndGet();
                return version;
            }
        });
    }

    /**
     * 获取指定键的当前版本号
     *
     * @param key 键
     * @return 版本号
     */
    private long getVersion(K key) {
        return versions.getOrDefault(key, new AtomicLong(0)).get();
    }

    /**
     * 确保对于同一个键，相关的操作只会被执行一次，并且其结果将被所有调用者共享.
     * 如果超时了没有complete，将会返回TimeoutException
     *
     * @param key      唯一key
     * @param function 要执行的方法函数
     * @param timeout  超时时间，单位为ms，默认3000ms=3s
     * @return
     */
    public CompletableFuture<V> doOrTimeout(K key, Function<K, V> function, long timeout, boolean useDistributedLock) {
        if (useDistributedLock && !acquireDistributedLock(key)) {
            CompletableFuture<V> future = new CompletableFuture<>();
            future.completeExceptionally(new IllegalStateException("Unable to acquire distributed lock"));
            return future;
        }

        try {
            long versionAtCallTime = getVersion(key);
            return ongoingOperations.compute(key, (k, existingFuture) -> {
                if (existingFuture == null || getVersion(k) != versionAtCallTime) {
                    CompletableFuture<V> future = new CompletableFuture<>();
                    CompletableFuture.runAsync(() -> {
                        try {
                            V result = function.apply(k);
                            future.complete(result);
                        } catch (Exception e) {
                            future.completeExceptionally(e);
                        } finally {
                            ongoingOperations.remove(k);
                        }
                    });

                    // 应用超时设置
                    return future.orTimeout(timeout, TimeUnit.MILLISECONDS);
                }
                return existingFuture;
            });
        } finally {
            if (useDistributedLock) {
                releaseDistributedLock(key);
            }
        }
    }


    /**
     * 当前方法会异步执行任务，并保证只有一个key能执行function任务，其他任务进行等待
     * 同时，如果执行失败，那么允许设定重试次数。并且再次执行function方法。
     *
     * @param key                 执行方法唯一key
     * @param function            要执行的任务
     * @param retries             重试次数
     * @param timeout             超时时间
     * @param delayBetweenRetries 重试前延迟时间
     * @return
     */
    public CompletableFuture<V> doOrRetry(K key, Function<K, V> function, int retries, long timeout,
                                          long delayBetweenRetries, boolean useDistributedLock) {
        if (useDistributedLock && !acquireDistributedLock(key)) {
            CompletableFuture<V> future = new CompletableFuture<>();
            future.completeExceptionally(new IllegalStateException("Unable to acquire distributed lock"));
            return future;
        }
        try {
            long versionAtCallTime = getVersion(key);
            return ongoingOperations.compute(key, (k, existingFuture) -> {
                if (existingFuture == null || getVersion(k) != versionAtCallTime) {
                    CompletableFuture<V> future = new CompletableFuture<>();
                    executeWithRetriesOrCompensate(future, key, function, null, retries, timeout, delayBetweenRetries
                            , versionAtCallTime);
                    return future;
                }
                return existingFuture;
            });
        } finally {
            if (useDistributedLock) {
                releaseDistributedLock(key);
            }
        }
    }

    /**
     * 当前方法会异步执行任务，并保证只有一个key能执行function任务，其他任务进行等待
     * 同时，如果执行失败，那么允许设定重试次数。并且执行compensation补偿方法。
     *
     * @param key                 执行方法唯一key
     * @param function            原有方法
     * @param compensation        补偿方法 在执行失败的时候执行
     * @param retries             重试次数
     * @param timeout             超时时间
     * @param delayBetweenRetries 重试前延迟时间
     * @return
     */
    public CompletableFuture<V> doOrCompensate(K key, Function<K, V> function, Function<K, V> compensation,
                                               int retries, long timeout, long delayBetweenRetries,
                                               boolean useDistributedLock) {
        if (useDistributedLock && !acquireDistributedLock(key)) {
            CompletableFuture<V> future = new CompletableFuture<>();
            future.completeExceptionally(new IllegalStateException("Unable to acquire distributed lock"));
            return future;
        }
        try {
            long versionAtCallTime = getVersion(key);
            return ongoingOperations.compute(key, (k, existingFuture) -> {
                if (existingFuture == null || getVersion(k) != versionAtCallTime) {
                    CompletableFuture<V> future = new CompletableFuture<>();
                    executeWithRetriesOrCompensate(future, key, function, compensation, retries, timeout,
                            delayBetweenRetries, versionAtCallTime);
                    return future;
                }
                return existingFuture;
            });
        } finally {
            if (useDistributedLock) {
                releaseDistributedLock(key);
            }
        }
    }

    /**
     * @param future
     * @param key
     * @param function
     * @param compensation
     * @param retries
     * @param timeout
     * @param delayBetweenRetries
     * @param versionAtCallTime
     */
    private void executeWithRetriesOrCompensate(CompletableFuture<V> future,
                                                K key, Function<K, V> function, Function<K, V> compensation,
                                                int retries, long timeout, long delayBetweenRetries,
                                                long versionAtCallTime) {
        CompletableFuture.runAsync(() -> {
                    try {
                        if (getVersion(key) != versionAtCallTime) {
                            throw new IllegalStateException("Data version changed");
                        }
                        V result = function.apply(key);
                        future.complete(result);
                    } catch (Exception e) {
                        if (retries > 0 && getVersion(key) == versionAtCallTime) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(delayBetweenRetries);
                            } catch (InterruptedException ignored) {
                            }
                            Function<K, V> nextFunction = (compensation != null) ? compensation : function;
                            executeWithRetriesOrCompensate(future, key, nextFunction, compensation, retries - 1,
                                    timeout, delayBetweenRetries, versionAtCallTime);
                        } else {
                            future.completeExceptionally(e);
                        }
                    }
                }).orTimeout(timeout, TimeUnit.MILLISECONDS)
                .exceptionally(ex -> {
                    if (retries > 0 && ex instanceof TimeoutException && getVersion(key) == versionAtCallTime) {
                        Function<K, V> nextFunction = (compensation != null) ? compensation : function;
                        executeWithRetriesOrCompensate(future, key, nextFunction, compensation, retries - 1, timeout,
                                delayBetweenRetries, versionAtCallTime);
                    } else {
                        future.completeExceptionally(ex);
                    }
                    return null;
                });
    }


    /**
     * 提供一个方式来异步地执行操作，并返回一个 CompletableFuture，
     * 该 CompletableFuture 可以让调用者在未来某个时刻获取操作的结果
     *
     * @param key
     * @param function
     * @return
     */
    public CompletableFuture<CompletableFuture<V>> doChan(K key, Function<K, V> function) {
        return CompletableFuture.completedFuture(ongoingOperations.computeIfAbsent(key, k -> {
            CompletableFuture<V> future = new CompletableFuture<>();
            CompletableFuture.runAsync(() -> {
                try {
                    V result = function.apply(k);
                    future.complete(result);
                } catch (Exception e) {
                    future.completeExceptionally(e);
                } finally {
                    ongoingOperations.remove(k);
                }
            });
            return future;
        }));
    }

    /**
     * 从 ongoingOperations 映射中移除了给定的键
     *
     * @param key
     */
    public void forget(K key) {
        ongoingOperations.remove(key);
    }


}


/**
 * 假设一个基于Redis的SingleFlight分布式锁实现
 * 从而使得SingleFlight支持分布式锁
 * @param <K>
 * @param <V>
 */
class RedisSingleFlight<K, V> extends SingleFlight<K, V> {
    // Redis 或其他分布式锁机制的实现

    @Override
    protected boolean acquireDistributedLock(K key) {
        return false;
    }

    @Override
    protected void releaseDistributedLock(K key) {

    }

    // 如果需要，可以添加特定于 Redis 的其他方法或逻辑
}