package blossom.project.towelove.framework.redis.service;

import blossom.project.towelove.framework.redis.enums.LuaPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2024/4/27 9:52 AM
 * RedisLuaService类
 */

@AutoConfiguration
public class RedisLuaService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 通用的 Lua 脚本执行方法。
     *
     * @param key Redis 键列表。
     * @return Lua 脚本的返回结果。
     */
    public boolean existKey(String key) {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(LuaPath.STR_LUA.getResourceScriptSource());
        redisScript.setResultType(Boolean.class);

        return (boolean) redisTemplate.execute(redisScript,
                Collections.singletonList(key),
                Collections.emptyList());
    }

    /**
     * 执行 Lua 脚本，传递参数。
     *
     * @param keys Redis 键列表。
     * @param args Lua 参数列表。
     * @param resultType 返回类型。
     * @param <T> 返回结果的类型。
     * @return Lua 脚本的返回结果。
     */
    public <T> T executeLuaScript(List<String> keys, List<String> args, Class<T> resultType) {
        DefaultRedisScript<T> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(LuaPath.STR_LUA.getResourceScriptSource());
        redisScript.setResultType(resultType);

        return redisTemplate.execute(redisScript, keys, args);
    }


}