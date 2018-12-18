package com.zhangbin.convention.lock.policy.backoff;

/**
 * @author zhangbin
 * @Type ReturnNullOffPolicy
 * @Desc 未获取锁返回 null
 * @date 2018-12-12
 * @Version V1.0
 */
public class ReturnNullOffPolicy implements BackOffPolicy {


    @Override
    public <T> T doBackOff() {
        return null;
    }
}
