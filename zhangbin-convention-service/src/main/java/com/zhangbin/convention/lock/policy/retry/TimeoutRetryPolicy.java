package com.zhangbin.convention.lock.policy.retry;

import java.util.function.BooleanSupplier;

/**
 * @author zhangbin
 * @Type TimeoutRetryPolicy
 * @Desc 单位时间内重试 默认 500 毫秒
 * @date 2018-12-12
 * @Version V1.0
 */
public class TimeoutRetryPolicy implements RetryPolicy {

    /**
     * 默认重试500毫秒
     */
    private static final long DEFAULT_TIMEOUT = 500;

    private long timeout;

    private long start;

    public TimeoutRetryPolicy() {
        this(DEFAULT_TIMEOUT);
    }

    public TimeoutRetryPolicy(long timeout) {
        this.timeout = timeout;
        this.start = System.currentTimeMillis();
    }

    @Override
    public boolean canTry(BooleanSupplier supplier) {
        for (;;) {
            if (!isAlive()) {
                return false;
            }
            if (supplier.getAsBoolean()) {
                return true;
            }
        }
    }

    private boolean isAlive() {
        return (System.currentTimeMillis() - start) <= timeout;
    }

}
