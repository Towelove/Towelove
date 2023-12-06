package blossom.project.towelove.framework.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/2/24 9:49
 * Description:
 */
@AutoConfiguration
public class RedisService {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 使用bitmap并且设定某一个位值
     *
     * @param key    bitmap缓存的键值
     * @param offset bitmap对应的索引位
     * @param value  bitmap对应的值 0/1
     * @return 是否设置成功
     */
    public boolean setBit(final String key, final Long offset, final Boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 获取bitmap某一个位上的值
     *
     * @param key    bitmap缓存的键值
     * @param offset bitmap对应的索引位
     * @return 该位键值是0/1
     */
    public boolean getBit(final String key, final Long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 获取BitMap中某个区间的值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long getBitByRange(final String key,final int start,final int end){
        return (Long) redisTemplate.execute((RedisCallback<Long>) conn ->{
            return conn.bitCount(key.getBytes(),start,end);
        } );
    }

    /**
     * 获取BitMap中的总值
     * @param key
     * @return
     */
    public Long getBitByRange(final String key){
        return (Long) redisTemplate.execute((RedisCallback<Long>) conn ->{
            return conn.bitCount(key.getBytes());
        } );
    }
    /**
     * 返回bitmap的长度
     *
     * @param key bitmap缓存的键值
     * @return 返回bitmap的长度
     */
    public long bitmapSize(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 获取某个bitmap上的1的个数
     *
     * @param key bitmap缓存的键值
     * @return 返回1的个数
     */
    public Long bitCount(final String key) {
        //Long start = 0L; // 起始位置
        //Long end = -1L; // 结束位置，-1表示计算整个bitmap的长度
        Long count = (Long) redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.bitCount(key.getBytes()));
        return count;
    }

    /**
     * 获取某个bitmap上某一段上的1的个数
     *
     * @param key bitmap缓存的键值
     * @return 返回1的个数
     */
    public Long bitCountRange(final String key, final Long start, final Long end) {
        Long length = (Long) redisTemplate.execute((RedisCallback<Long>) con ->
                con.bitCount(key.getBytes(), start, end));
        return length;
    }

    /**
     * 是哦那个bitfield获取连续为1的天数
     *
     * @param buildSignKey bitmap缓存的键值
     * @param limit
     * @param offset
     * @return
     */
    @Deprecated
    public List<Long> bitField(final String buildSignKey, final Integer limit, final Long offset) {
        return (List<Long>) redisTemplate.execute((RedisCallback<List<Long>>) con ->
                con.bitField(buildSignKey.getBytes(),
                        BitFieldSubCommands.create()
                                .get(BitFieldSubCommands.BitFieldType
                                        .unsigned(limit)).valueAt(offset)));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection collection) {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取某个key中的元素数量
     *
     * @param key
     * @return
     */
    public int getCacheSetSize(final String key) {
        return redisTemplate.opsForSet().members(key).size();
    }

    /**
     * 返回集合中是否存在输入的元素
     *
     * @param key    集合的键
     * @param member 要查找的元素集合
     * @return 之查找一个元素则使用map.get(key)即可，
     * 返回的是一个map集合
     */
    public Map<Object, Boolean> hasMemberInSet(Object key, Objects... member) {
        if (member.length > 1) {
            return redisTemplate.opsForSet().isMember(key, member);
        } else {
            Boolean aBoolean = redisTemplate.opsForSet().isMember(key, member[0]);
            Map map = new HashMap();
            map.put(key, aBoolean);
            return map;
        }
    }

    /**
     * 返回两个集合的交集
     * @param key 集合1名称
     * @param otherKey 集合2名称
     * @param collections JVM中自定义的集合
     * @return 两个集合的交集
     * @param <T>
     */
    public <T> Set<T> setUnion(final String key, @Nullable final String otherKey, Set<T> collections) {
        if (!hasKey(key)) {
            throw new RuntimeException("当前"+key+"不存在");
        }
        if (!hasKey(otherKey)) {
            throw new RuntimeException("当前"+otherKey+"不存在");
        }
        if (otherKey == null) {
            Set union = redisTemplate.opsForSet().union(key, collections);
            return union;
        } else {
            Set union = redisTemplate.opsForSet().union(key, otherKey);
            return union;
        }
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    public  boolean hasHashValue(final String key,final String hKey){
        return redisTemplate.opsForHash().hasKey(key,hKey);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}
