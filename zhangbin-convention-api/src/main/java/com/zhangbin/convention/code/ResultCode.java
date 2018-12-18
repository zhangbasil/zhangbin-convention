package com.zhangbin.convention.code;

/**
 * @author zhangbin
 * @Type ResultCode
 * @Desc 返回结果码接口
 * @date 2018-10-17
 * @Version V1.0
 */
public interface ResultCode {

    /**
     * 系统异常相应码标识
     */
    String SYS_PREFIX = "SYS_";

    /**
     * 通用相应码标识
     */
    String COMMON_PREFIX = "C_";

    String code();

    String message();

}
