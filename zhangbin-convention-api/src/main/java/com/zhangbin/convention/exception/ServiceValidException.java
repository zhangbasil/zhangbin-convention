package com.zhangbin.convention.exception;

import com.zhangbin.convention.code.CommonCode;
import com.zhangbin.convention.result.Result;

import java.util.List;

/**
 * @author zhangbin
 * @Type ServiceValidException
 * @Desc
 * @date 2018-10-19
 * @Version V1.0
 */
public class ServiceValidException extends ServiceException {

    private List<Result.ViolationItem> violationItems;

    public ServiceValidException(List<Result.ViolationItem> violationItems) {
        super(CommonCode.INVALID_ARGS.code(), CommonCode.INVALID_ARGS.message());
        this.violationItems = violationItems;
    }



    public List<Result.ViolationItem> getViolationItems() {
        return violationItems;
    }

    public String getDetailMessage() {
        return null;
    }
}
