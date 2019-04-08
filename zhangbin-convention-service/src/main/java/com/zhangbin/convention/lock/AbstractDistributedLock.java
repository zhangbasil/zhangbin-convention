package com.zhangbin.convention.lock;

import com.zhangbin.convention.lock.policy.retry.RetryPolicy;
import com.zhangbin.convention.lock.policy.retry.SampleRetryPolicy;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhangbin
 * @Type AbstractDistributedLock
 * @Desc
 * @date 2018-12-20
 * @Version V1.0
 */
public abstract class AbstractDistributedLock implements DistributedLock {

    // 默认过期时间 60 * 10 秒
    static final long DEFAULT_LEASE_SECONDS = 60 * 10;

    @Value("${spring.application.name}")
    protected String applicationName;

    @Override
    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, new SampleRetryPolicy());
    }

    @Override
    public boolean tryLock(String lockKey, RetryPolicy retryPolicy) {
        return acquire(createLockKey(lockKey), retryPolicy);
    }

    @Override
    public void unlock(String lockKey) {
        release(createLockKey(lockKey));
    }

    /**
     * 获取锁模板方法
     *
     * @param lockKey 分布式锁key
     * @param retryPolicy 重试策略
     * @return
     */
    public abstract boolean acquire(String lockKey, RetryPolicy retryPolicy);

    /**
     * 释放锁模板方法
     *
     * @param lockKey 分布式锁key
     */
    public abstract void release(String lockKey);

    /**
     * 创建锁的key
     *
     * @param key
     * @return
     */
    public abstract String createLockKey(String key);


}
