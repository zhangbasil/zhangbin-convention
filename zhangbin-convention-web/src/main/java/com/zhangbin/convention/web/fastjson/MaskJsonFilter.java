package com.zhangbin.convention.web.fastjson;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangbin
 * @Type MaskJsonFilter
 * @Desc
 * @date 2019-01-09
 * @Version V1.0
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MaskJsonFilter {

    Class<?> clazz();

    MaskField[] props();
}
