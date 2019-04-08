package com.zhangbin.convention.web.fastjson;

import com.zhangbin.convention.field.Mask;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangbin
 * @Type MaskField
 * @Desc
 * @date 2019-01-11
 * @Version V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@Inherited
public @interface MaskField {

    String name();

    Mask pattern();

}
