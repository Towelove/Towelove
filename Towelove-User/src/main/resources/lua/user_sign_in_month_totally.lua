local prefix = KEYS[1]
local bitCounts = 0

local keys = redis.call('keys', prefix)

for i,key in ipairs(keys) do
    local bitCount = redis.call('bitcount', key)
    bitCounts = bitCounts + bitCount
end

return bitCounts
