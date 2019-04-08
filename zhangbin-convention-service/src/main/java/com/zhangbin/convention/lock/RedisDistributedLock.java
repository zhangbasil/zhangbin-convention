package com.zhangbin.convention.lock;

import com.zhangbin.convention.lock.policy.retry.RetryPolicy;
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
public class RedisDistributedLock extends AbstractDistributedLock {

    @Autowired(required = false)
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean acquire(String lockKey, RetryPolicy retryPolicy) {
        if (setNX(lockKey)) {
            return true;
        }
        return retryPolicy.canTry(() -> setNX(lockKey));
    }

    @Override
    public void release(String lockKey) {
        Boolean delSuccess = redisTemplate.delete(lockKey);
        if (Objects.isNull(delSuccess) || !delSuccess) {
            throw new IllegalStateException("删除分布式redis锁失败：lockKey = " + lockKey);
        }
    }

    @Override
    public String createLockKey(String key) {
        return applicationName + ":" + key;
    }

    private boolean setNX(String lockKey) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", DEFAULT_LEASE_SECONDS,
                TimeUnit.SECONDS);
        return Objects.nonNull(success) && success;
    }

}
