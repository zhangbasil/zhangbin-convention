package com.zhangbin.convention.fastjson;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.zhangbin.convention.field.Mask;
import com.zhangbin.convention.util.StringUtils;

import java.lang.reflect.AccessibleObject;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zhangbin
 * @Type MaskFastJsonValueFilter
 * @date 2019-01-11
 * @Version V1.0
 */
public class MaskFastJsonValueFilter implements ContextValueFilter {

    @Override
    public Object process(BeanContext context, Object object, String name, Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        String valueStr = value.toString();
        if (String.class != value.getClass() || StringUtils.isBlank(valueStr)) {
            return value;
        }
        Optional<String> fieldOptional = mask(context.getField(), valueStr);
        if (fieldOptional.isPresent()) {
            return fieldOptional.get();
        }
        Optional<String> methodOptional = mask(context.getMethod(), valueStr);
        return methodOptional.orElse(valueStr);
    }


    private Optional<String> mask(AccessibleObject accessibleObject, String value) {
        Mask maskField = accessibleObject.getAnnotation(Mask.class);
        if (Objects.isNull(maskField)) {
            return Optional.empty();
        }
        return doMask(maskField, value);
    }

    /**
     * 对属性做掩码
     *
     * @param maskField 掩码的属性注解规则
     * @param value 掩码属性的值
     * @return
     */
    public Optional<String> doMask(Mask maskField, String value) {
        String left = StringUtils.left(value, maskField.left());
        String placeholder = StringUtils.repeat(maskField.placeholder(), maskField.repeat());
        String right = StringUtils.right(value, maskField.right());
        return Optional.of(left + placeholder + right);
    }

}
