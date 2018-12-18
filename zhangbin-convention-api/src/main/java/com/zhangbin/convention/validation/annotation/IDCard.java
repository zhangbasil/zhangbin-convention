package com.zhangbin.convention.validation.annotation;

import com.zhangbin.convention.validation.validator.IDCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangbin
 * @Type IDCard
 * @Desc
 * @date 2018-10-19
 * @Version V1.0
 */
@Constraint(validatedBy = { IDCardValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Documented
public @interface IDCard {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 是否允许15位身份证通过验证
     */
    boolean allowOld() default false;

    /**
     * 是否校验18位身份证最后一位校验位
     */
    boolean checkLastCode() default false;
}
