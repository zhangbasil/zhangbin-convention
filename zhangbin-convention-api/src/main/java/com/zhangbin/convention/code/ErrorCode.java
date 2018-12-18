package com.zhangbin.convention.code;

/**
 * @author zhangbin
 * @Type ErrorCode
 * @Desc
 * @date 2018-10-18
 * @Version V1.0
 */
public enum ErrorCode implements ResultCode {

    UNKNOWN_ERROR("1", "未知的系统错误"),
    CALL_SERVICE_ERROR("2", "调用服务异常"),
    DB_ERROR("3", "数据库异常"),
    CACHE_ERROR("4", "缓存异常"),
    HTTP_ERROR("5", "调用HTTP接口发生异常"),
    RETURN_NULL_ERROR("6", "服务不能返回空对象"),
    ;


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return SYS_PREFIX + code;
    }

    @Override
    public String message() {
        return message;
    }

}
