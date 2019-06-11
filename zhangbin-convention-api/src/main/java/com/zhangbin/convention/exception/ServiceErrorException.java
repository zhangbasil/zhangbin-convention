package com.zhangbin.convention.exception;

import com.zhangbin.convention.code.ErrorCode;

/**
 * @author zhangbin
 * @Type ServiceErrorException
 * @Desc 系统服务异常 例如：DB连接异常
 * @date 2018-10-18
 * @Version V1.0
 */
public class ServiceErrorException extends RuntimeException {

    private String code;

    public ServiceErrorException() {
        this(ErrorCode.UNKNOWN_ERROR);
    }

    public ServiceErrorException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceErrorException(Throwable cause) {
        super(cause);
    }

    public ServiceErrorException(ErrorCode errorCode) {
        super(errorCode.message());
        this.code = errorCode.code();
    }

    public ServiceErrorException error(ErrorCode errorCode) {
        return new ServiceErrorException(errorCode);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
