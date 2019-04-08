package com.zhangbin.convention.web.advice;

import com.zhangbin.convention.result.Result;

/**
 * @author zhangbin
 * @Type GlobalRestControllerCustomExceptionHandler
 * @Desc
 * @date 2019-01-11
 * @Version V1.0
 */
public interface GlobalRestControllerCustomExceptionHandler {
    /**
     * 自定义全局restController异常增强，返回值不可为空，如果为空，则忽略
     * 本方法只针对restController有效
     *
     * @param throwable 发生的异常
     * @return result
     */
    Result handleGlobalRestControllerException(Throwable throwable);
}
