package com.zhangbin.convention.lock;

import com.zhangbin.convention.lock.policy.retry.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbin
 * @Type ZookeeperDistributedLock
 * @Desc
 * @date 2018-12-20
 * @Version V1.0
 */
public class ZookeeperDistributedLock extends AbstractDistributedLock {

    private final CuratorFramework curatorFramework;

    private ThreadLocal<InterProcessMutex> mutexThreadLocal = new ThreadLocal<>();

    public ZookeeperDistributedLock(String zkAddress) {
        curatorFramework = CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();
    }

    @Override
    public boolean acquire(String lockKey, RetryPolicy retryPolicy) {
        try {
            InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, lockKey);
            mutexThreadLocal.set(interProcessMutex);
            return interProcessMutex.acquire(DEFAULT_LEASE_SECONDS, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new IllegalStateException("获取分布式zookeeper锁失败：lockKey = " + lockKey);
        }
    }

    @Override
    public void release(String lockKey) {
        try {
            mutexThreadLocal.get().release();
        } catch (Exception e) {
            throw new IllegalStateException("删除分布式zookeeper锁失败：lockKey = " + lockKey);
        }
    }

    @Override
    public String createLockKey(String key) {
        return "/" + applicationName + "/" + key;
    }

}
