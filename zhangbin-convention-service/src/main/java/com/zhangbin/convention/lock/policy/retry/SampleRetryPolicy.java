package com.zhangbin.convention.lock.policy.retry;

import java.util.function.BooleanSupplier;

/**
 * @author zhangbin
 * @Type DefaultRetryPolicy
 * @Desc 默认简单按照次数重试
 * @date 2018-12-11
 * @Version V1.0
 */
public class SampleRetryPolicy implements RetryPolicy {

    /**
     * 默认最多重试次数
     */
    private final static int DEFAULT_MAX_ATTEMPTS = 5;

    /**
     * 最大重试次数
     */
    private int maxAttempts;

    public SampleRetryPolicy() {
        this.maxAttempts = DEFAULT_MAX_ATTEMPTS;
    }

    public SampleRetryPolicy(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Override
    public boolean canTry(BooleanSupplier supplier) {
        int retry = maxAttempts;
        for (;;) {
            if (retry < 1) {
                return false;
            }
            if (supplier.getAsBoolean()) {
                return true;
            }
            --retry;
        }
    }
}
