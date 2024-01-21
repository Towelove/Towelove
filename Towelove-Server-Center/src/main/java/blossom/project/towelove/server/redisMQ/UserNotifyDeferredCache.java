package blossom.project.towelove.server.redisMQ;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.framework.redis.util
 * @className: UserNotifyDeferredCache
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/21 16:09
 * @version: 1.0
 */
@Component
@RequiredArgsConstructor
public class UserNotifyDeferredCache {

//    private final RedisService redisService;
//
//    public   boolean isExist(Long requestId){
//        return redisService.hasHashValue(UserNotifyConstants.USER_NOTIFY_REFERRED_RESULT, String.valueOf(requestId));
//    }
//
//    public Consumer<Result<?>> get(Long key){
//        return redisService.getCacheMapValue(UserNotifyConstants.USER_NOTIFY_REFERRED_RESULT,String.valueOf(key));
//    }
//
//    public void save(Long userId,Consumer<Result<?>> value){
//        redisService.setCacheMapValue(UserNotifyConstants.USER_NOTIFY_REFERRED_RESULT,String.valueOf(userId),value);
//    }
//
//    public Consumer<Result<?>> remove(Long key) {
//        Consumer<Result<?>> cacheMapValue = redisService.getCacheMapValue(UserNotifyConstants.USER_NOTIFY_QUEUE, String.valueOf(key));
//        redisService.deleteCacheMapValue(UserNotifyConstants.USER_NOTIFY_REFERRED_RESULT, String.valueOf(key));
//        return cacheMapValue;
//    }

    private final Map<Long,Consumer<Result<?>>> map = new ConcurrentHashMap<>();

    public boolean isExist(Long userId){
        return map.containsKey(userId);
    }

    public Consumer<Result<?>> get(Long key){
        return map.getOrDefault(key,null);
    }

    public void save(Long userId,Consumer<Result<?>> value){
        map.putIfAbsent(userId,value);
    }

    public Consumer<Result<?>> remove(Long key) {
        return map.remove(key);
    }



}
