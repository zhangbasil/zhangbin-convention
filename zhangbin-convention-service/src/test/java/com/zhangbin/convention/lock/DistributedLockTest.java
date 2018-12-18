package com.zhangbin.convention.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbin
 * @Type DistributedLockTest
 * @Desc
 * @date 2018-12-08
 * @Version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-lock.xml" })
public class DistributedLockTest {

    @Resource
    private DistributedLock distributedLock;

    private static final int THREAD_NUM = 20;

    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
    private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

    @Test
    public void dealWithLockTest() {
        multiThread();
    }

    public void multiThread() {

        Runnable runnable = () -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            doSomething();

        };

        for (int i = 0; i < THREAD_NUM; i++) {
            executorService.execute(runnable);
            countDownLatch.countDown();
        }

//        executorService.shutdown();
        try {
            executorService.awaitTermination(20000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void doSomething(){

        String result = distributedLock.doDefaultLock(() -> {

            return "200";
        }, "test:lock");



        System.out.println("result = " + result);

    }

}
