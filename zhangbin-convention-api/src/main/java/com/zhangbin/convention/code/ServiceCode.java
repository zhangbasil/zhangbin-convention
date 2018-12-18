package com.zhangbin.convention.code;

import com.zhangbin.convention.exception.ServiceException;

import java.text.MessageFormat;

/**
 * @author zhangbin
 * @Type ServiceCode
 * @Desc 业务错误码接口
 * @date 2018-10-17
 * @Version V1.0
 */
public interface ServiceCode extends ResultCode {

    default ServiceException failure() {
        return new ServiceException(this.code(), this.message());
    }

    default ServiceException failure(String message) {
        return new ServiceException(this.code(), message);
    }

    default ServiceException failure(Throwable cause) {
        return new ServiceException(this, cause);
    }

    /**
     * message里面有占位符的情况
     *
     * @param values 占位符值
     * @return
     */
    default ServiceException placeholderFailure(Object... values) {
        return new ServiceException(this.code(), MessageFormat.format(this.message(), values));
    }

}
