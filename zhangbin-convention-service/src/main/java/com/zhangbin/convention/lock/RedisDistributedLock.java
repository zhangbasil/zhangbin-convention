package com.zhangbin.convention.lock;

import com.zhangbin.convention.lock.policy.retry.RetryPolicy;
import com.zhangbin.convention.lock.policy.retry.SampleRetryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author zhangbin
 * @Type RedisDistributedLock
 * @Desc
 * @date 2018-12-06
 * @Version V1.0
 */
public class RedisDistributedLock implements DistributedLock {

    private static final String SET_COMMAND_NX = "NX";
    private static final String SET_COMMAND_EX = "EX";
    private static final String RES_OK = "OK";

    // 默认过期时间 60 * 10 秒
    private static final long DEFAULT_LEASE_SECONDS = 60 * 10;

    @Autowired(required = false)
    private JedisPool jedisPool;



    @Override
    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, new SampleRetryPolicy());
    }

    @Override
    public boolean tryLock(String lockKey, RetryPolicy retryPolicy) {
        return tryRedisLock(lockKey, retryPolicy);
    }


    private boolean tryRedisLock(String uniqueKey, RetryPolicy retryPolicy) {
        if (setNX(uniqueKey)) {
            return true;
        }
        return retryPolicy.canTry(() -> setNX(uniqueKey));
    }

    @Override
    public void unlock(String uniqueKey) {
        Long result = del(uniqueKey);
        if (result < 1) {
            throw new IllegalStateException("删除分布式redis锁失败：uniqueKey = " + uniqueKey);
        }
    }


    private boolean setNX(String key) {
        return RES_OK.equals(jedisConn(command -> command.set(key, "1", SET_COMMAND_NX, SET_COMMAND_EX, DEFAULT_LEASE_SECONDS)));
    }

    private Long del(String key) {
        return jedisConn(command -> command.del(key));
    }

    /**
     * Jedis 连接
     *
     * @param command redis 操作命令
     * @param <R> 返回类型
     * @return
     */
    private <R> R jedisConn(Function<Jedis, R> command) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return command.apply(jedis);
        } finally {
            if (Objects.nonNull(jedis)) {
                jedis.close();
            }
        }
    }

}
