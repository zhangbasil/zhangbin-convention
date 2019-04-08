package com.zhangbin.convention.aop.exception;

import com.zhangbin.convention.result.Result;

import java.lang.reflect.Method;

/**
 * @author zhangbin
 * @Type ExternalExceptionHandler
 * @Desc 除约定以外的异常的其他异常
 * @date 2018-10-21
 * @Version V1.0
 */
public interface ExternalExceptionHandler {

    /**
     * 其他未约定异常
     *
     * @param method 发生异常的方法
     * @param args 发生异常的方法参数值
     * @param throwable 发生的异常
     * @return Result
     */
    Result handle(Throwable throwable, Method method, Object[] args);
}
