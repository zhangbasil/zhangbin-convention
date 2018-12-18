package com.zhangbin.convention.lock.policy.backoff;

import com.zhangbin.convention.code.CommonCode;

/**
 * @author zhangbin
 * @Type ThrowExceptionBackOffPolicy
 * @Desc 未获取锁抛出异常
 * @date 2018-12-12
 * @Version V1.0
 */
public class ThrowExceptionBackOffPolicy implements BackOffPolicy {


    @Override
    public <T> T doBackOff() {
        throw CommonCode.TRY_LOCK_FAIL.failure();
    }
}
