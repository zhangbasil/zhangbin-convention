package com.zhangbin.convention.lock;

import com.zhangbin.convention.lock.policy.backoff.BackOffPolicy;
import com.zhangbin.convention.lock.policy.backoff.ThrowExceptionBackOffPolicy;
import com.zhangbin.convention.lock.policy.retry.RetryPolicy;
import com.zhangbin.convention.lock.policy.retry.SampleRetryPolicy;

import java.util.function.Supplier;

/**
 * @author zhangbin
 * @Type DistributedLock
 * @Desc
 * @date 2018-12-07
 * @Version V1.0
 */
public interface DistributedLock {

    /**
     * 尝试获取锁 获取锁失败默认重试5次后抛出异常
     *
     * @param lockKey 分布式锁key
     * @return
     */
    boolean tryLock(String lockKey);

    boolean tryLock(String lockKey, RetryPolicy retryPolicy);

    /**
     * 解锁
     *
     * @param lockKey 分布式锁key
     */
    void unlock(String lockKey);

    /**
     * 默认分布式锁实现
     *
     * @param supplier 分布式锁的内容
     * @param lockKey 分布式锁key
     * @param <R> 返回值类型
     * @return R
     */
    default <R> R doDefaultLock(Supplier<R> supplier, String lockKey) {
        return doWithLock(supplier, lockKey, new SampleRetryPolicy(), new ThrowExceptionBackOffPolicy());
    }

    default <R> R doWithLock(Supplier<R> supplier, String lockKey, RetryPolicy retryPolicy,
            BackOffPolicy backOffPolicy) {
        if (!tryLock(lockKey, retryPolicy)) {
            return backOffPolicy.doBackOff();
        }
        try {
            return supplier.get();
        } finally {
            unlock(lockKey);
        }
    }

}
