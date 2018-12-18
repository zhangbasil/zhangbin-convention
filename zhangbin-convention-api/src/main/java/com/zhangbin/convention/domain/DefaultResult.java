package com.zhangbin.convention.domain;

import com.zhangbin.convention.code.ErrorCode;
import com.zhangbin.convention.code.SuccessCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbin
 * @Type DefaultResult
 * @Desc
 * @date 2018-10-20
 * @Version V1.0
 */
public class DefaultResult<T> implements Result<T> {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 异常类名
     */
    private String errorClass;

    /**
     * 详细异常信息
     */
    private String errorStack;

    /**
     * 参数校验错误信息
     */
    private List<ViolationItem> violationItems;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public List<ViolationItem> getViolationItems() {
        return violationItems;
    }

    @Override
    public String getErrorClass() {
        return errorClass;
    }

    @Override
    public String getErrorStack() {
        return errorStack;
    }

    @Override
    public DefaultResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public DefaultResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public DefaultResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public DefaultResult<T> setViolationItems(List<ViolationItem> violationItems) {
        this.violationItems = violationItems;
        return this;
    }

    @Override
    public DefaultResult<T> setErrorClass(String errorClass) {
        this.errorClass = errorClass;
        return this;
    }

    @Override
    public DefaultResult<T> setErrorStack(String errorStack) {
        this.errorStack = errorStack;
        return this;
    }

    @Override
    public DefaultResult<T> addViolationItem(String field, String message) {
        if (violationItems == null) {
            violationItems = new ArrayList<>();
        }
        violationItems.add(new DefaultViolationItem(field, message));
        return this;
    }

    @Override
    public boolean isSuccess() {
        return SuccessCode.SUCCESS.code().equals(code);
    }

    @Override
    public boolean isError() {
        return code != null && code.startsWith(ErrorCode.SYS_PREFIX);
    }

    @Override
    public boolean isFailure() {
        return (!isSuccess()) && (!isError());
    }

    public static class DefaultViolationItem implements ViolationItem {

        private String field;
        private String message;

        public DefaultViolationItem(String field, String message) {
            this.field = field;
            this.message = message;
        }

        @Override
        public String getField() {
            return field;
        }

        @Override
        public void setField(String field) {
            this.field = field;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "{" + "field='" + field + '\'' + ", message='" + message + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "Result{" + "code='" + code + '\'' + ", message='" + message + '\'' + ", data=" + data + ", errorClass='"
                + errorClass + '\'' + ", errorStack='" + errorStack + '\'' + ", violationItems=" + violationItems + '}';
    }
}
