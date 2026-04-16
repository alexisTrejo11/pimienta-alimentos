-- Token bucket (atomic refill + consume).
-- KEYS[1]   : Redis hash key holding bucket state
-- ARGV[1]   : capacity (burst, max tokens)
-- ARGV[2]   : refill rate (tokens per second)
-- ARGV[3]   : current time in milliseconds (UTC)
--
-- Returns a 3-element array: { allowed (1|0), remaining_tokens (int), retry_after_seconds (int) }

local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local refill_per_sec = tonumber(ARGV[2])
local now = tonumber(ARGV[3])

if capacity == nil or capacity < 1 then
  return {1, 0, 0}
end
if refill_per_sec == nil then
  refill_per_sec = 0
end

local data = redis.call('HMGET', key, 'tokens', 'last_ts')
local tokens
local last_ts

if data[1] == false or data[1] == nil then
  tokens = capacity
  last_ts = now
else
  tokens = tonumber(data[1])
  last_ts = tonumber(data[2])
  if tokens == nil then tokens = capacity end
  if last_ts == nil then last_ts = now end
end

-- Refill based on elapsed wall time since last update
local elapsed_sec = (now - last_ts) / 1000.0
if elapsed_sec > 0 and refill_per_sec > 0 then
  tokens = math.min(capacity, tokens + (refill_per_sec * elapsed_sec))
end

local allowed = 0
local retry_after = 0

if tokens >= 1.0 then
  tokens = tokens - 1.0
  allowed = 1
else
  if refill_per_sec > 0 then
    local needed = 1.0 - tokens
    retry_after = math.ceil(needed / refill_per_sec)
  else
    retry_after = 60
  end
  if retry_after < 1 then
    retry_after = 1
  end
end

redis.call('HSET', key, 'tokens', tostring(tokens), 'last_ts', tostring(now))
-- Avoid unbounded keys if a client stops calling
redis.call('EXPIRE', key, 172800)

local remaining = math.floor(tokens)
if remaining < 0 then
  remaining = 0
end

return { allowed, remaining, retry_after }
