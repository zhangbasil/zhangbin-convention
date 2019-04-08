package com.zhangbin.convention.web.fastjson;

import com.alibaba.fastjson.support.spring.FastJsonContainer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.MappingFastJsonValue;
import com.zhangbin.convention.fastjson.MaskFastJsonValueFilter;
import com.zhangbin.convention.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


@ControllerAdvice
public class MaskFastJsonViewResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaskFastJsonViewResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return FastJsonHttpMessageConverter.class.isAssignableFrom(converterType)
                && returnType.hasMethodAnnotation(MaskJsonView.class);
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        MaskJsonView annotation = returnType.getMethodAnnotation(MaskJsonView.class);
        if (Objects.isNull(annotation)) {
            return body;
        }
        MaskJsonFilter[] maskFastJsonFilters = annotation.mask();

        Object maskObj;
        if (body instanceof FastJsonContainer) {
            FastJsonContainer fastJsonContainer = (FastJsonContainer) body;
            Object value = fastJsonContainer.getValue();
            maskObj = (value instanceof MappingFastJsonValue) ? ((MappingFastJsonValue) value).getValue() : value;
        } else {
            maskObj = (body instanceof MappingFastJsonValue) ? ((MappingFastJsonValue) body).getValue() : body;
        }

        for (MaskJsonFilter maskFilter : maskFastJsonFilters) {
            Class<?> maskClass = maskFilter.clazz();
            MaskField[] maskFields = maskFilter.props();
            if (maskClass == maskObj.getClass()) {
                doMaskField(maskObj, maskFields);
            }
            doRecursiveMask(maskObj, maskClass, maskFields);
        }
        return body;
    }

    /**
     * 递归查找掩码属性类型所在 当前对象属性的位置
     *
     * @param maskObj    值类型
     * @param maskClass  掩码的类
     * @param maskFields
     */
    private void doRecursiveMask(Object maskObj, Class<?> maskClass, MaskField[] maskFields) {
        Field[] declaredFields = maskObj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Class<?> fieldType = field.getType();
            if (ClassUtils.isSampleType(fieldType)) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(maskObj);
                // 遍历属性类型是否匹配
                if (fieldType == maskClass) {
                    doMaskField(fieldValue, maskFields);
                } else if (Collection.class.isAssignableFrom(fieldType)) {
                    // 如果是 Collection 遍历判断集合里面的对象
                    Collection collection = (Collection) fieldValue;
                    for (Object obj : collection) {
                        if (obj.getClass() == maskClass) {
                            doMaskField(obj, maskFields);
                        }
                    }
                } else {
                    // 递归对象里面的其他属性
                    doRecursiveMask(fieldValue, maskClass, maskFields);
                }

            } catch (IllegalAccessException e) {
                LOGGER.warn("" + e.getMessage(), e);
            }
        }
    }


    private void doMaskField(Object object, MaskField[] maskFields) {
        for (MaskField maskField : maskFields) {
            try {
                String fieldName = maskField.name();
                Field declaredField = object.getClass().getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                Object fieldValue = declaredField.get(object);
                MaskFastJsonValueFilter sensitiveValueFilter = new MaskFastJsonValueFilter();
                Optional<String> optional = sensitiveValueFilter.doMask(maskField.pattern(), fieldValue.toString());
                if (optional.isPresent()) {
                    declaredField.set(object, optional.get());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                LOGGER.warn("" + e.getMessage(), e);
            }

        }
    }



}
