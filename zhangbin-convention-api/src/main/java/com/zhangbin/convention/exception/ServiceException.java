package com.zhangbin.convention.exception;

import com.zhangbin.convention.code.ServiceCode;

/**
 * @author zhangbin
 * @Type ServiceException
 * @Desc 业务异常
 * @date 2018-10-17
 * @Version V1.0
 */
public class ServiceException extends RuntimeException {

    protected String code;

    public ServiceException() {
        super();
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(ServiceCode respCode) {
        super(respCode.message());
        this.code = respCode.code();
    }

    public ServiceException(ServiceCode respCode, Throwable cause) {
        super(respCode.message(), cause);
        this.code = respCode.code();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetailMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "ServiceException{" + "code='" + code + '\'' + ",message='" + getMessage() + '\'' + '}';
    }
}
