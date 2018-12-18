package com.zhangbin.convention.lock.policy.retry;

import java.util.function.BooleanSupplier;

/**
 * @author zhangbin
 * @Type NeverRetryPolicy
 * @Desc 永不重试
 * @date 2018-12-11
 * @Version V1.0
 */
public class NeverRetryPolicy implements RetryPolicy {

    @Override
    public boolean canTry(BooleanSupplier supplier) {
        return false;
    }
}
