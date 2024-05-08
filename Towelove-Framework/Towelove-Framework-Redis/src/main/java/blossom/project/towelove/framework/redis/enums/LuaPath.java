package blossom.project.towelove.framework.redis.enums;

import blossom.project.towelove.common.constant.RedisKeyConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author: 张锦标
 * @date: 2024/4/27 10:01 AM
 * LuaPath 枚举类
 */
@Getter
@AllArgsConstructor
public enum LuaPath {
    STR_LUA(RedisKeyConstant.STR_LUA,
            new ResourceScriptSource(new ClassPathResource(RedisKeyConstant.STR_LUA)),
            "字符串操作 Lua 脚本"),

    LIST_LUA(RedisKeyConstant.LIST_LUA,
            new ResourceScriptSource(new ClassPathResource(RedisKeyConstant.LIST_LUA)),
            "列表操作 Lua 脚本");
    private final String path;
    private final ResourceScriptSource resourceScriptSource;
    private final String description; // 使用更明确的字段名
}
