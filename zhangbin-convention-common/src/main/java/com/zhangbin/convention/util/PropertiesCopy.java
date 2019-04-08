package com.zhangbin.convention.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.zhangbin.convention.fastjson.MaskFastJsonValueFilter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhangbin
 * @Type PropertiesCopy
 * @Desc 使用FastJson 做对象转换
 * @date 2019-04-03
 * @Version V1.0
 */
public class PropertiesCopy {

    public static <T> T copyBean(Object source, Class<T> target) {
        return copyBean(source, target, true);
    }

    public static <T> T copyBean(Object source, Class<T> target, boolean needMask) {
        SerializeFilter serializeFilter = null;
        if (needMask) {
            serializeFilter = new MaskFastJsonValueFilter();
        }
        String sourceJson = JSON.toJSONString(source, serializeFilter);
        return JSON.parseObject(sourceJson, target);
    }

    public static <S, T> List<T> copyBeans(List<S> source, Class<T> target) {
        if (Objects.isNull(source)) {
            return Collections.emptyList();
        }
        return source.stream().map(item -> copyBean(item, target)).collect(Collectors.toList());
    }


}
