package com.zhangbin.convention.code;

/**
 * @author zhangbin
 * @Type SuccessCode
 * @Desc 成功相应码
 * @date 2018-10-18
 * @Version V1.0
 */
public enum SuccessCode implements ResultCode {

    SUCCESS("0", "成功"),;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
