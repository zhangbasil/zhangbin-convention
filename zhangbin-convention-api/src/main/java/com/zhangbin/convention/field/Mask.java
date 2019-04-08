package com.zhangbin.convention.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangbin
 * @Type Mask
 * @Desc 掩码注解 例如：176****1809  张**
 * @date 2019-01-11
 * @Version V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
public @interface Mask {


    /**
     * 掩码占位符
     *
     * @return
     */
    String placeholder() default "*";

    /**
     * placeholder 重复的次数
     *
     * @return
     */
    int repeat() default 4;

    /**
     * 保留掩码起始位置；例如：手机号 -> 1*********
     *
     * @return
     */
    int left() default 1;

    /**
     * 保留掩码结束位置；例如：手机号 -> 1*********9
     *
     * @return
     */
    int right() default 1;

}
