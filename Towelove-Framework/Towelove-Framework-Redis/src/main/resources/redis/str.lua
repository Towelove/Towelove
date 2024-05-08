-- Lua 脚本：根据命令执行不同的操作
local command = ARGV[1]  -- 获取传入的命令

if command == "exists" then
    -- 检查键是否存在
    local key = KEYS[1]
    local exists = redis.call("EXISTS", key)

    -- 如果键存在，返回 true；否则返回 false
    if exists == 1 then
        return true
    else
        return false
    end

elseif command == "delete" then
    -- 删除键
    local key = KEYS[1]
    redis.call("DEL", key)
    return true

else
    return false
end
