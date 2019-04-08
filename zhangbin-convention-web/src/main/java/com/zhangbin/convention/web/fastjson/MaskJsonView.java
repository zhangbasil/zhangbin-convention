package com.zhangbin.convention.web.fastjson;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangbin
 * @Type MaskJsonView
 * @Desc
 * @date 2019-01-11
 * @Version V1.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MaskJsonView {

    MaskJsonFilter[] mask() default {};

}
