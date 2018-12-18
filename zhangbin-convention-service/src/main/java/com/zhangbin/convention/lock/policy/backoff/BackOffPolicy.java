package com.zhangbin.convention.lock.policy.backoff;


/**
 * @author zhangbin
 * @Type BackOffPolicy
 * @Desc 未获取分布式锁需要做的策略
 * @date 2018-12-12
 * @Version V1.0
 */
public interface BackOffPolicy {

    <T> T doBackOff();

}
