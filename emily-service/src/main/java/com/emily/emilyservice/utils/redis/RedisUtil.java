package com.emily.emilyservice.utils.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import com.emily.emilyservice.utils.serialize.SerializeUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

@Component
@Primary
public class RedisUtil implements Redis {

    private Log logger = LogFactory.getLog(this.getClass());

    private static final String errorMsg = "Redis出现错误";

    @Value("${spring.redis.host:127.0.0.1}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private int redisPort;

    @Value("${spring.redis.password:}")
    private String redisPassword;

    @Value("${spring.redis.max.idel:500}")
    private int redisMaxIdel;

    private int timeout = 1000;

    private JedisPool pool = null;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(redisHost)) {
            logger.info("redisHost没配置(spring.redis.host),不初始化redis客户端");
            return;
        }
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(redisMaxIdel);
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 30);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            if (StringUtils.isEmpty(redisPassword)) {
                pool = new JedisPool(config, redisHost, redisPort, timeout);
            } else {
                pool = new JedisPool(config, redisHost, redisPort, timeout, redisPassword);
            }
            logger.info("pool:" + pool);
        } catch (Exception e) {
            throw new RuntimeException("不能初始化Redis客户端", e);
        }
    }

    public Object get(String key) {
        return exe(jedis -> SerializeUtil.unserialize(jedis.get(key.getBytes())));
    }

    public Jedis getJedis() {
        return pool.getResource();
    }

    public void set(String key, Object value) {
        exe(jedis -> jedis.set(key.getBytes(), SerializeUtil.serialize(value)));
    }

    /**
     * 设置过期时间
     *
     * @param timeout
     *            单位秒
     */
    public void expire(String key, int timeout) {
        exe(jedis -> jedis.expire(key, timeout));
    }

    public void set(String key, Object value, int second) {
        exe(jedis -> jedis.setex(key.getBytes(), second, SerializeUtil.serialize(value)));
    }

    public void hset(String key, String field, Object value) {
        exe(jedis -> jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value)));
    }

    public Object hget(String key, String field) {
        return exe(jedis -> SerializeUtil.unserialize(jedis.hget(key.getBytes(), field.getBytes())));
    }

    public void remove(String key) {
        exe(jedis -> jedis.del(key));
    }

    public long getAutoId(String key) {
        return exe(jedis -> jedis.incr(key));
    }

    public void lpush(String key, Object value) {
        exe(jedis -> jedis.lpush(key.getBytes(), SerializeUtil.serialize(value)));
    }

    public void rpush(String key, Object value) {
        exe(jedis -> jedis.rpush(key.getBytes(), SerializeUtil.serialize(value)));
    }

    public List<Object> lrange(String key) {
        return exe(jedis -> {
            List<byte[]> list = jedis.lrange(key.getBytes(), 0, -1);
            List<Object> result = new ArrayList<Object>(list.size());
            for (byte[] o : list) {
                result.add(SerializeUtil.unserialize(o));
            }
            return result;
        });
    }

    public Object lpop(String key) {
        return exe(jedis -> SerializeUtil.unserialize(jedis.lpop(key.getBytes())));
    }

    public Object rpop(String key) {
        return exe(jedis -> SerializeUtil.unserialize(jedis.rpop(key.getBytes())));
    }

    public Object lindex(String key, int index) {
        return exe(jedis -> SerializeUtil.unserialize(jedis.lindex(key.getBytes(), index)));
    }

    public void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public List<String> keys(String pattern) {
        return exe(jedis -> new ArrayList<String>(jedis.keys(pattern)));
    }

    public List<String> keys() {
        return keys("*");
    }

    public long llen(String key) {
        return exe(jedis -> jedis.llen(key));
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public boolean tryLock(String key) {
        Jedis jedis = null;
        boolean lock = false;
        try {
            jedis = pool.getResource();
            Long exist = jedis.setnx(key.getBytes(), key.getBytes());
            if (exist > 0) {
                lock = true;
            }
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        } finally {
            close(jedis);
        }
        return lock;
    }

    public boolean tryLock(String key, int timeout) {
        Jedis jedis = null;
        boolean lock = false;
        try {
            jedis = pool.getResource();
            Long exist = jedis.setnx(key.getBytes(), key.getBytes());
            if (exist > 0) {
                lock = true;
                jedis.expire(key.getBytes(), timeout);
            }
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        } finally {
            close(jedis);
        }
        return lock;
    }

    public void releaseLock(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key.getBytes());
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        } finally {
            close(jedis);
        }
    }

    /**
     * 如果 key 已经存在，并且值为字符串，那么这个命令会把 value 追加到原来值（value）的结尾。 如果 key
     * 不存在，那么它将首先创建一个空字符串的key，再执行追加操作，这种情况 APPEND 将类似于 SET 操作。
     */
    public void append(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.append(key.getBytes(), SerializeUtil.serialize(value));
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public boolean exist(String key) {
        return exe(jedis -> jedis.exists(key));
    }

    @Override
    public Set<String> hkeys(String key) {
        return exe(jedis -> jedis.hkeys(key));
    }

    @Override
    public void lrem(String key, long count, String value) {
        exe(jedis -> jedis.lrem(key.getBytes(), count, SerializeUtil.serialize(value)));
    }

    @Override
    public long getNum(String key) {
        Jedis jedis = null;
        long id = 0L;
        try {
            jedis = pool.getResource();
            String autoId = (String) jedis.get(key);
            if(!StringUtils.isEmpty(autoId)) {
                id = NumberUtils.toLong(autoId);
            }
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        } finally {
            close(jedis);
        }
        return id;
    }

    @Override
    public void clearAutoId(String key) {
        remove(key);
    }

    @Override
    public void setString(String key, String value) {
        exe(jedis -> jedis.set(key, value));
    }

    @Override
    public void expireString(String key, int timeoutSecond) {
        expire(key, timeoutSecond);
    }

    @Override
    public void setString(String key, String value, int second) {
        exe(jedis -> jedis.setex(key, second, value));
    }

    @Override
    public void hsetString(String key, String field, String value) {
        exe(jedis -> jedis.hset(key, field, value));
    }

    @Override
    public String hgetString(String key, String field) {
        return exe(jedis -> jedis.hget(key, field));
    }

    @Override
    public void removeString(String key) {
        remove(key);
    }

    @Override
    public void lpushString(String key, String value) {
        exe(jedis -> jedis.lpush(key, value));
    }

    @Override
    public void rpushString(String key, String value) {
        exe(jedis -> jedis.rpush(key, value));
    }

    @Override
    public List<String> lrangeString(String key) {
        return exe(jedis -> jedis.lrange(key, 0, -1));
    }

    @Override
    public String lpopString(String key) {
        return exe(jedis -> jedis.lpop(key));
    }

    @Override
    public String rpopString(String key) {
        return exe(jedis -> jedis.rpop(key));
    }

    @Override
    public String lindexString(String key, int index) {
        return exe(jedis -> jedis.lindex(key, index));
    }

    @Override
    public List<String> keysString(String pattern) {
        return exe(jedis -> new ArrayList<String>(jedis.keys(pattern)));
    }

    @Override
    public List<String> keysString() {
        return keysString("*");
    }

    @Override
    public long llenString(String key) {
        return exe(jedis -> jedis.llen(key));
    }

    @Override
    public void appendString(String key, String value) {
        exe(jedis -> jedis.append(key, value));
    }

    @Override
    public boolean existString(String key) {
        return exe(jedis -> jedis.exists(key));
    }

    @Override
    public Set<String> hkeysString(String key) {
        return exe(jedis -> jedis.hkeys(key));
    }

    @Override
    public void lremString(String key, long count, String value) {
        exe(jedis -> jedis.lrem(key, count, value));
    }

    @Override
    public void saddString(String setName, String[] setValues) {
        exe(jedis -> jedis.sadd(setName, setValues)).intValue();
    }

    @Override
    public void saddObject(String setName, Object[] setValues) {
        for (int i = 0; i < setValues.length; i++) {
            Object setValue = setValues[i];
            exe(jedis -> jedis.sadd(setName.getBytes(), SerializeUtil.serialize(setValue))).intValue();
        }
    }

    @Override
    public void sremString(String setName, String[] setValues) {
        exe(jedis -> jedis.srem(setName, setValues));
    }

    @Override
    public void sremObject(String setName, Object[] setValues) {
        for (int i = 0; i < setValues.length; i++) {
            Object setValue = setValues[i];
            exe(jedis -> jedis.srem(setName.getBytes(), SerializeUtil.serialize(setValue)));
        }
    }

    @Override
    public String spopString(String setName) {
        return exe(jedis -> jedis.spop(setName));
    }

    @Override
    public Set<String> smembers(String setName) {
        return exe(jedis -> jedis.smembers(setName));
    }

    @Override
    public Set<String> sdiff(String[] setNames) {
        return exe(jedis -> jedis.sdiff(setNames));
    }

    @Override
    public void sdiffstore(String[] setNames, String dstsetName) {
        exe(jedis -> jedis.sdiffstore(dstsetName, setNames));
    }

    @Override
    public Set<String> sinter(String[] setNames) {
        return exe(jedis -> jedis.sinter(setNames));
    }

    @Override
    public void sinterstore(String[] setNames, String dstsetName) {
        exe(jedis -> jedis.sinterstore(dstsetName, setNames));
    }

    @Override
    public Set<String> sunion(String[] setNames) {
        return exe(jedis -> jedis.sunion(setNames));
    }

    @Override
    public void sunionstore(String[] setNames, String dstsetName) {
        exe(jedis -> jedis.sunionstore(dstsetName, setNames));
    }

    @Override
    public void smove(String srcsetName, String dstsetName, String value) {
        exe(jedis -> jedis.smove(dstsetName, dstsetName, value));
    }

    @Override
    public Long scard(String setName) {
        return exe(jedis -> jedis.scard(setName));
    }

    @Override
    public Boolean sismember(String setName, String value) {
        return exe(jedis -> jedis.sismember(setName, value));
    }

    @Override
    public List<String> srandmember(String setName, int count) {
        return exe(jedis -> jedis.srandmember(setName, count));
    }

    @Override
    public void zadd(String setName, double score, String value) {
        exe(jedis -> jedis.zadd(setName, score, value));
    }

    @Override
    public void zrem(String setName, String[] value) {
        exe(jedis -> jedis.zrem(setName, value));
    }

    @Override
    public void zincrby(String setName, double increment, String value) {
        exe(jedis -> jedis.zincrby(setName, increment, value));
    }

    @Override
    public Long zrank(String setName, String value) {
        return exe(jedis -> jedis.zrank(setName, value));
    }

    @Override
    public Long zrevrank(String setName, String value) {
        return exe(jedis -> jedis.zrevrank(setName, value));
    }

    @Override
    public Set<String> zrevrange(String setName, int start, int end) {
        return exe(jedis -> jedis.zrevrange(setName, start, end));
    }

    @Override
    public Set<String> zrangeByScore(String setName, int min, int max) {
        return exe(jedis -> jedis.zrangeByScore(setName, min, max));
    }

    @Override
    public Long zcount(String setName, int min, int max) {
        return exe(jedis -> jedis.zcount(setName, min, max));
    }

    @Override
    public Long zcard(String setName) {
        return exe(jedis -> jedis.zcard(setName));
    }

    @Override
    public Double zscore(String setName, String value) {
        return exe(jedis -> jedis.zscore(setName, value));
    }

    @Override
    public void zremrangeByRank(String setName, long start, long end) {
        exe(jedis -> jedis.zremrangeByRank(setName, start, end));
    }

    @Override
    public void zremrangeByScore(String setName, double min, double max) {
        exe(jedis -> jedis.zremrangeByScore(setName, min, max));
    }

    @Override
    public String getString(String key) {
        return exe(jedis -> jedis.get(key));
    }

    private <T> T exe(Function<Jedis, T> fun) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return fun.apply(jedis);
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public void hdel(String key, String field) {
        exe(jedis -> jedis.hdel(key.getBytes(), field.getBytes()));
    }

    @Override
    public void hdelString(String key, String field) {
        exe(jedis -> jedis.hdel(key, field));
    }

    @Override
    public <T> Map<T, Object> batchGet(Collection<T> entities, Function<T, String> getKeyFunc) {
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }
        if (getKeyFunc == null) {
            return new HashMap<>();
        }
        return exe(jedis -> {
            Pipeline pipeline = jedis.pipelined();
            List<Response<byte[]>> responses = new ArrayList<>(entities.size());
            Map<T, Response<byte[]>> responseMap = new HashMap<>();
            for (T entity : entities) {
                responseMap.put(entity, pipeline.get(getKeyFunc.apply(entity).getBytes()));
            }
            pipeline.sync();
            Map<T, Object> resultMap = new HashMap<>();
            for (Map.Entry<T, Response<byte[]>> entry : responseMap.entrySet()) {
                Response<byte[]> response = entry.getValue();
                if (responses != null && ArrayUtils.isNotEmpty(response.get())) {
                    resultMap.put(entry.getKey(), SerializeUtil.unserialize(response.get()));
                }
            }
            return resultMap;
        });
    }

    @Override
    public <T> Map<T, String> batchGetString(Collection<T> entities, Function<T, String> getKeyFunc) {
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }
        if (getKeyFunc == null) {
            return new HashMap<>();
        }
        return exe(jedis -> {
            Pipeline pipeline = jedis.pipelined();
            List<Response<String>> responses = new ArrayList<>(entities.size());
            Map<T, Response<String>> responseMap = new HashMap<>();
            for (T entity : entities) {
                responseMap.put(entity, pipeline.get(getKeyFunc.apply(entity)));
            }
            pipeline.sync();
            Map<T, String> resultMap = new HashMap<>();
            for (Map.Entry<T, Response<String>> entry : responseMap.entrySet()) {
                Response<String> response = entry.getValue();
                if (responses != null && StringUtils.isNotBlank(response.get())) {
                    resultMap.put(entry.getKey(), response.get());
                }
            }
            return resultMap;
        });
    }

    @Override
    public <T> void batchSet(Collection<T> entities, Function<T, String> getKeyFunc, int seconds) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        if (getKeyFunc == null) {
            return;
        }
        exe(jedis -> {
            Pipeline pipeline = jedis.pipelined();
            for (T entity : entities) {
                pipeline.set(getKeyFunc.apply(entity).getBytes(), SerializeUtil.serialize(entity));
                pipeline.expire(getKeyFunc.apply(entity).getBytes(), seconds);
            }
            return pipeline.syncAndReturnAll();
        });
    }
}
