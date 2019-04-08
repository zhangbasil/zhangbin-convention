package com.zhangbin.convention.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author zhangbin
 * @Type ClassUtils
 * @Desc
 * @date 2019-04-04
 * @Version V1.0
 */
public class ClassUtils {


    public static boolean isSampleType(Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Integer.class
                || clazz == Long.class
                || clazz == Float.class
                || clazz == Double.class
                || clazz == BigInteger.class
                || clazz == BigDecimal.class
                || clazz == String.class
                || clazz == java.util.Date.class
                || clazz == java.sql.Date.class
                || clazz == java.sql.Time.class
                || clazz == java.sql.Timestamp.class
                || clazz == java.time.LocalDateTime.class
                || clazz == java.time.LocalDate.class
                || clazz == java.time.LocalTime.class
                || clazz.isEnum();
    }
}
