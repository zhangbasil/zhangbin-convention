package com.zhangbin.convention.aop.exception;

import java.lang.reflect.Method;

/**
 * @author zhangbin
 * @Type ExceptionAlarmHandler
 * @Desc 异常告警
 * @date 2019-02-18
 * @Version V1.0
 */
public interface ExceptionAlarmHandler {

    void notice(Throwable throwable, Method method, Object[] args);

}
