package com.zhangbin.convention.result;

import com.zhangbin.convention.code.CommonCode;
import com.zhangbin.convention.code.ErrorCode;
import com.zhangbin.convention.code.ServiceCode;
import com.zhangbin.convention.code.SuccessCode;
import com.zhangbin.convention.exception.ServiceErrorException;
import com.zhangbin.convention.exception.ServiceException;
import com.zhangbin.convention.page.DefaultResult;
import com.zhangbin.convention.result.Result;

import java.util.List;

/**
 * @author zhangbin
 * @Type Results
 * @Desc
 * @date 2018-10-20
 * @Version V1.0
 */
public final class Results {

    /**
     * 成功
     *
     * @return Result<Void>
     */
    public static Result<Void> success() {
        return new DefaultResult<Void>().setCode(SuccessCode.SUCCESS.code()).setMessage(SuccessCode.SUCCESS.message());
    }

    /**
     * 成功
     *
     * @param data 并设置data参数
     * @param <T> data的泛型
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        return new DefaultResult<T>().setCode(SuccessCode.SUCCESS.code()).setMessage(SuccessCode.SUCCESS.message())
                .setData(data);
    }

    public static <T> Result<T> invalid() {
        return new DefaultResult<T>().setCode(CommonCode.INVALID_ARGS.code())
                .setMessage(CommonCode.INVALID_ARGS.message());
    }

    public static <T> Result<T> invalid(String message) {
        return new DefaultResult<T>().setCode(CommonCode.INVALID_ARGS.code()).setMessage(message);
    }

    public static <T> Result<T> invalid(String message, List<Result.ViolationItem> violationItems) {
        return new DefaultResult<T>().setCode(CommonCode.INVALID_ARGS.code()).setMessage(message)
                .setViolationItems(violationItems);
    }

    public static <T> Result<T> invalid(List<Result.ViolationItem> violationItems) {
        return new DefaultResult<T>().setCode(CommonCode.INVALID_ARGS.code())
                .setMessage(CommonCode.INVALID_ARGS.message()).setViolationItems(violationItems);
    }

    /**
     * 服务异常，即业务逻辑异常 serviceCode
     *
     * @param serviceCode 用serviceCode的好处就是强制让大家去继承并实现serviceCode
     * @param <T> 对应data字段的数据类型
     * @return Result<T>
     */
    public static <T> Result<T> failure(ServiceCode serviceCode) {
        return new DefaultResult<T>().setCode(serviceCode.code()).setMessage(serviceCode.message());
    }

    public static <T> Result<T> failure(ServiceException serviceException) {
        return new DefaultResult<T>().setCode(serviceException.getCode()).setMessage(serviceException.getMessage());
    }

    /**
     * 返回带异常信息的响应结果，当成系统错误（bug）来处理
     *
     * @param e 异常
     */
    public static <T> Result<T> error(ServiceErrorException e) {
        return new DefaultResult<T>().setCode(e.getCode()).setMessage(e.getMessage());
    }

    public static <T> Result<T> error() {
        return new DefaultResult<T>().setCode(ErrorCode.UNKNOWN_ERROR.code())
                .setMessage(ErrorCode.UNKNOWN_ERROR.message());
    }

    /**
     *
     * @param errorCode 错误码
     * @param <T>       对应data字段的数据类型
     * @return result 对象
     */
    public static <T> Result<T> error(ErrorCode errorCode) {
        return new DefaultResult<T>().setCode(errorCode.code()).setMessage(errorCode.code());

    }

    /**
     * 返回带异常信息的响应结果，可以自己明确的系统错误
     *
     * @param code 错误编号
     * @param message 错误信息
     * @param <T> 对应data字段的数据类型
     * @return result 对象
     */
    public static <T> Result<T> error(String code, String message) {
        return new DefaultResult<T>().setCode(code).setMessage(message);
    }

    /**
     * 构建参数验证失败的项目
     *
     * @param fieldName 字段名称
     * @param message 信息
     * @return 参数验证失败的项目
     */
    public static Result.ViolationItem buildViolationItem(String fieldName, String message) {
        return new DefaultResult.DefaultViolationItem(fieldName, message);
    }

}
