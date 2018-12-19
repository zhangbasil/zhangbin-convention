package com.zhangbin.convention.lock;

import com.zhangbin.convention.lock.policy.retry.RetryPolicy;
import com.zhangbin.convention.lock.policy.retry.SampleRetryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbin
 * @Type RedisDistributedLock
 * @Desc
 * @date 2018-12-06
 * @Version V1.0
 */
public class RedisDistributedLock implements DistributedLock {

    // 默认过期时间 60 * 10 秒
    private static final long DEFAULT_LEASE_SECONDS = 60 * 10;

    @Autowired(required = false)
    private RedisTemplate<String, String> redisTemplate;

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
        Boolean delSuccess = redisTemplate.delete(uniqueKey);
        if (Objects.isNull(delSuccess) || !delSuccess) {
            throw new IllegalStateException("删除分布式redis锁失败：uniqueKey = " + uniqueKey);
        }
    }

    private boolean setNX(String key) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "1", DEFAULT_LEASE_SECONDS, TimeUnit.SECONDS);
        return Objects.nonNull(success) && success;
    }

}
