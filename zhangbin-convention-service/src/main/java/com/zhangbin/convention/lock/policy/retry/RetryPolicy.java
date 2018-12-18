package com.zhangbin.convention.lock.policy.retry;

import java.util.function.BooleanSupplier;

/**
 * @author zhangbin
 * @Type RetryPolicy
 * @Desc 获取锁失败的重试策略
 * @date 2018-12-11
 * @Version V1.0
 */
public interface RetryPolicy {

    boolean canTry(BooleanSupplier supplier);

}
