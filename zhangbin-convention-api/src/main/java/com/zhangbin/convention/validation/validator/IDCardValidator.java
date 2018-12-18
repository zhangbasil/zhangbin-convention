package com.zhangbin.convention.validation.validator;

import com.zhangbin.convention.validation.annotation.IDCard;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhangbin
 * @Type IDCardValidator
 * @Desc 身份证格式校验
 * @date 2018-10-19
 * @Version V1.0
 */
public class IDCardValidator implements ConstraintValidator<IDCard, String> {

    private static final int CARD_LEN_FIFTEEN = 15;
    private static final int CARD_LEN_EIGHTEEN = 18;

    private boolean allowOld;
    private boolean checkLastCode;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        if (value.length() == CARD_LEN_EIGHTEEN) {
            return IDCardValidatorUtils.validate(value, checkLastCode);
        } else {
            return allowOld && value.length() == CARD_LEN_FIFTEEN && IDCardValidatorUtils.validateOld(value);
        }
    }

    @Override
    public void initialize(IDCard constraintAnnotation) {
        this.allowOld = constraintAnnotation.allowOld();
        this.checkLastCode = constraintAnnotation.checkLastCode();
    }
}
