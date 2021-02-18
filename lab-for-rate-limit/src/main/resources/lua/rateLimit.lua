local key = KEYS[1] --限流的key。通过KEY[1]获取传入的key参数
local limit = tonumber(ARGV[1]) -- 限流大小。通过ARGV[1]获取传入的limit参数
print("limit```" .. limit)
local current = tonumber(redis.call('get',key) or "0") --从缓存中获取key相关的值，如果不存在返回0
if current + 1 > limit then -- 判断缓存中记录的数字是否大于限制大小，超出表示要被限流，返回0。 没超过就将缓存值+1，设置过期时间为2s，返回值为1
return 0
else
redis.call("INCRBY", key, "1")
redis.call("expire", key,"2")
return 1
end