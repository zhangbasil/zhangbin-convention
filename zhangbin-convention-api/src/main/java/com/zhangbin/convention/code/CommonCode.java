package com.zhangbin.convention.code;

/**
 * @author zhangbin
 * @Type CommonCode
 * @Desc 公共错误码
 * @date 2018-10-21
 * @Version V1.0
 */
public enum CommonCode implements ServiceCode {
    INVALID_ARGS("1", "参数无效"),
    DATA_NOT_FOUND("2", "当前数据不存在"),
    NO_LOGIN("3", "用户未登录"),
    INVALID_AUTH("4", "权限无效"),
    RECORD_EXISTED("5", "已经存在该记录"),
    TRY_LOCK_FAIL("6", "获取分布式锁失败"),
    ;

    private final String code;
    private final String message;

    CommonCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return COMMON_PREFIX + code;
    }

    @Override
    public String message() {
        return message;
    }
}
