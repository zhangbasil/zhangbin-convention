package com.zhangbin.convention.aop.exception;

import com.zhangbin.convention.domain.Result;

import java.lang.reflect.Method;

/**
 * @author zhangbin
 * @Type ReturnResultExceptionHandler
 * @Desc
 * @date 2018-10-21
 * @Version V1.0
 */
public interface ReturnResultExceptionHandler {

    /**
     * 自定义异常增强，返回值不可为空，如果为空，则忽略
     *
     * @param method 发生异常的方法
     * @param args 发生异常的方法参数值
     * @param throwable 发生的异常
     * @return Result
     */
    Result handleReturnResultException(Throwable throwable, Method method, Object[] args);
}
