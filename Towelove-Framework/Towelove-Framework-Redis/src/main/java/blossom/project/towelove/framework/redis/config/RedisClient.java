package blossom.project.towelove.framework.redis.config;

import blossom.project.towelove.common.utils.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Redis客户端， 目前支持以下数据结构
 * （如需要使用其他数据类型，请联系 max.shi@nio.com）：
 * String key -> String value String key -> List<String> values String key -> bytes value <p>
 */
@AutoConfiguration
public class RedisClient {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Add key -> bytes value
     * @param key
     * @param value
     * @param seconds
     *         存活时间，单位是秒
     */
    public void setBytes(String key, byte[] value, long seconds) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(value != null, "Required non-blank param 'value'");
        checkState(seconds > 0, "Param 'value' must > 0");

        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * Get bytes value by key.
     * @param key
     * @param retain
     * @return
     */
    public byte[] getBytes(String key, boolean retain) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        Object obj = redisTemplate.opsForValue().get(key);
        if (!retain) {
            redisTemplate.delete(key);
        }
        return (byte[]) obj;
    }

    /**
     * Add key -> string value
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(StringUtils.isNotBlank(value), "Required non-blank param 'value'");

        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * Add key -> string value
     * @param key
     * @param value
     * @param seconds
     *         存活时间，单位是秒
     */
    public void set(String key, String value, long seconds) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(StringUtils.isNotBlank(value), "Required non-blank param 'value'");
        checkState(seconds > 0, "Required param 'seconds' must > 0");

        stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * Add key -> string value
     * @param key
     * @param value
     * @param timeToLive
     *         存活时间
     * @param timeUnit
     *         时间单位
     */
    public void set(String key, String value, long timeToLive, TimeUnit timeUnit) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(StringUtils.isNotBlank(value), "Required non-blank param 'value'");
        checkState(timeToLive > 0, "Required param 'timeToLive' must > 0");
        checkState(timeUnit != null, "Required non-null param 'timeUnit'");

        stringRedisTemplate.opsForValue().set(key, value, timeToLive, timeUnit);
    }


    public boolean setIfAbsent(String key, String value) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(StringUtils.isNotBlank(value), "Required non-blank param 'value'");

        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }


    /**
     * Get string value by key.
     * @param key
     * @return
     */
    public String get(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * Delete value by key.
     * @param key
     */
    public void delete(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        stringRedisTemplate.delete(key);
    }

    /**
     * Delete value by keys.
     * @param keys
     */
    public void multiDelete(Collection<String> keys) {
        checkState(CollectionUtils.isNotEmpty(keys), "Required non-null param 'keys'");

        stringRedisTemplate.delete(keys);
    }

    /**
     * 创建列表,超时时间
     * @param key
     * @param values
     * @param seconds
     */
    public void createStringList(String key, List<String> values, long seconds) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(CollectionUtils.isNotEmpty(values), "Required non-empty param 'values'");
        checkState(seconds > 0, "Required param 'seconds' must > 0");

        BoundListOperations<String, String> opt = stringRedisTemplate.boundListOps(key);
        if (opt.size() != 0L) {
            throw new IllegalStateException("There was already a non-empty list in redis !");
        }
        opt.rightPushAll(values.toArray(new String[0]));
        opt.expire(seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取整个列表
     * @param key
     * @return
     */
    public List<String> getStringList(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }


    public void leftPushToStringList(String key, String value) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(StringUtils.isNotBlank(value), "Required non-blank param 'value'");

        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public void leftPushAllToStringList(String key, String... values) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(ArrayUtils.isNotEmpty(values), "Required non-empty param 'values'");

        stringRedisTemplate.opsForList().leftPushAll(key, values);
    }

    public void leftPushAllToStringList(String key, Collection<String> values) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(CollectionUtils.isNotEmpty(values), "Required non-empty param 'values'");

        stringRedisTemplate.opsForList().leftPushAll(key, values);
    }

    public void rightPushToStringList(String key, String value) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(StringUtils.isNotBlank(value), "Required non-blank param 'value'");

        stringRedisTemplate.opsForList().rightPush(key, value);
    }

    public void rightPushAllToStringList(String key, String... values) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(ArrayUtils.isNotEmpty(values), "Required non-empty param 'values'");

        stringRedisTemplate.opsForList().rightPushAll(key, values);
    }

    public void rightPushAllToStringList(String key, Collection<String> values) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(CollectionUtils.isNotEmpty(values), "Required non-empty param 'values'");

        stringRedisTemplate.opsForList().rightPushAll(key, values);
    }

    public String leftPopFromStringList(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return stringRedisTemplate.opsForList().leftPop(key);
    }

    public String rightPopFromStringList(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return stringRedisTemplate.opsForList().rightPop(key);
    }

    public void rightPushAllToLongList(String key, Collection<Long> values) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(CollectionUtils.isNotEmpty(values), "Required non-empty param 'values'");

        redisTemplate.opsForList().rightPushAll(key, values);
    }

    public void rightPushAllToLongList(String key, Long... values) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(ArrayUtils.isNotEmpty(values), "Required non-empty param 'values'");

        redisTemplate.opsForList().rightPushAll(key, values);
    }

    public Long rightPopFromLongList(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return (Long) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * add string to set
     * @param key
     * @param values
     */
    public void addToStringSet(String key, String... values) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(ArrayUtils.isNotEmpty(values), "Required non-empty param 'values'");

        stringRedisTemplate.opsForSet().add(key, values);
    }

    /**
     * pop string from set
     * @param key
     * @return
     */
    public String popFromStringSet(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return stringRedisTemplate.opsForSet().pop(key);
    }

    /**
     * get size of set
     * @param key
     * @return
     */
    public Long sizeOfStringSet(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return stringRedisTemplate.opsForSet().size(key);
    }

    /**
     * 获取列表头部的值
     * @param key
     * @return
     */
    public String leftGetFromStringList(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return stringRedisTemplate.opsForList().index(key, 0);
    }

    /**
     * 获取列表尾部的值
     * @param key
     * @return
     */
    public String rightGetFromStringList(String key) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");

        return stringRedisTemplate.opsForList().index(key, -1);
    }


    /**
     * 设置一个key的过期时间
     * @param key
     * @param timeout
     *         存活时间
     * @param unit
     *         时间单位
     * @return
     */
    public Boolean expire(String key, final long timeout, final TimeUnit unit) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(timeout > 0, "Param 'timeout' must > 0");
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 对一个key所对应的值按照指定步数递增
     * @param key
     * @param step
     * @return
     */
    public long increment(String key, final long step) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(step > 0, "Param 'step' must > 0");
        return redisTemplate.opsForValue().increment(key, step);
    }
    
    /**
     * 对一个key所对应的值按照指定步数递减
     * @param key
     * @param step
     * @return
     */
    public long decrement(String key, final long step) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(step > 0, "Param 'step' must > 0");
        return redisTemplate.opsForValue().increment(key, Math.negateExact(step));
    }

    /**
     * 判断redis key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return StringUtils.isNotBlank(key) && (redisTemplate.hasKey(key));
    }

    /**
     * 获取list指定区间内的所有元素
     *
     * !!!注意redis.list的range操作是闭区间。即start=0，end=10返回11个元素。
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, int start, int end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 查询list的长度
     * @param key
     * @return
     */
    public long listLength(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    /**
     * 批量查询。
     *
     * 查询结果集与参数集中的元素一一对应，且如果键在redis中不存在，则对应的值为null。
     * @param keys
     * @return
     */
    public List<String> multiGet(List<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 批量set
     * @param map
     */
    public void multiSet(Map<String, String> map) {
        checkState(map != null, "Required not-null param 'map'");
        stringRedisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 带有效期的批量set
     * @param map
     * @param milliSeconds 存活时间，单位是毫秒
     */
    public void multiSet(Map<String, String> map, long milliSeconds) {
        checkState(map != null, "Required not-null param 'map'");
        checkState(milliSeconds > 0, "Required param 'seconds' must > 0");

        executePipelined(redisConnection -> {
            RedisSerializer<String> serializer = new StringRedisSerializer();
            map.forEach((k, v) -> {
                redisConnection.set(serializer.serialize(k), serializer.serialize(v));
                redisConnection.pExpire(serializer.serialize(k), milliSeconds);
            });
            return null;
        });
    }

    /**
     * 使用pipeline批量处理
     * @param action
     * @return
     */
    public List<Object> executePipelined(RedisCallback<?> action){
        return stringRedisTemplate.executePipelined(action);
    }


    /**
     * 更新key对象field的值
     * @param key
     * @param field
     *         缓存对象field
     * @param value
     *         缓存对象field值
     */
    public void hSet(String key, String field, String value) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(StringUtils.isNotBlank(field), "Required non-empty param 'field'");
        checkState(value != null, "Required not-null param 'value'");
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 更新key对象field的值
     * @param key
     * @param map
     *         缓存对象field:value
     */
    public void hSet(String key, Map<String, String> map) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(map != null, "Required not-null param 'map'");
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 查询key对象field的值
     * @param key
     * @param field
     *         域:field
     */
    public String hGet(String key, String field) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(StringUtils.isNotBlank(field), "Required non-empty param 'field'");
        Object o = stringRedisTemplate.opsForHash().get(key, field);
        return null == o? null : o.toString();
    }

    /**
     * 查询key对象多个field的值
     * @param key
     * @param fields
     */
    public List<Object> multiHGet(String key, List<Object> fields) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        checkState(CollectionUtils.isNotEmpty(fields), "Required non-empty param 'fields'");
        return stringRedisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * 删除HashMap，或者HashMap中的值
     * @param key
     * @param field
     *         域:field
     */
    public Long hDel(String key, String field) {
        checkState(StringUtils.isNotBlank(key), "Required non-blank param 'key'");
        Long size = -1L;
        if (StringUtils.isNotEmpty(field)) {
            size = stringRedisTemplate.opsForHash().delete(key, field);
        } else {
            stringRedisTemplate.opsForHash().getOperations().delete(key);
        }
        return size;
    }

    public void convertAndSend(String channel, Object message) {
        checkState(StringUtils.isNotBlank(channel), "Required non-blank param 'channel'");
        checkState(Objects.nonNull(message), "Required non-null param 'message'");
        stringRedisTemplate.convertAndSend(channel, message);
    }

    public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
        checkNotNull(script,"Required param 'script'");
        return stringRedisTemplate.execute(script, keys, args);
    }

}
