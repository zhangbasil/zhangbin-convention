package com.zhangbin.convention.internal.util;

import com.zhangbin.convention.result.Result;
import com.zhangbin.convention.result.Results;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangbin
 * @Type ValidationUtils
 * @Desc 验证工具类 仅限模块内部使用，开发中请不要使用这个类
 * @date 2018-10-20
 * @Version V1.0
 */
public class ValidationUtils {

    public static List<Result.ViolationItem> convertToResultViolationItems(
            Set<? extends ConstraintViolation<?>> constraintViolations, ParameterNameDiscoverer parameterNameDiscoverer,
            Method method) {
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return Collections.emptyList();
        }
        return constraintViolations.stream().map(constraintViolation -> {
            String fieldName = getFieldName(parameterNameDiscoverer, method, constraintViolation);
            return Results.buildViolationItem(fieldName, constraintViolation.getMessage());
        }).collect(Collectors.toList());
    }


    public static String convertToOutMessage(Set<? extends ConstraintViolation<?>> constraintViolations,
                                             ParameterNameDiscoverer parameterNameDiscoverer, Method method) {
        StringBuilder outMessage = new StringBuilder();
        outMessage.append("`");
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return outMessage.append("}").toString();
        }
        constraintViolations.forEach(constraintViolation -> {
            String fieldName = getFieldName(parameterNameDiscoverer, method, constraintViolation);
            String message = constraintViolation.getMessage();
            Object invalidValue = constraintViolation.getInvalidValue();
            outMessage.append(fieldName)
                    .append("(")
                    .append(Objects.nonNull(invalidValue) ? invalidValue.toString() : null)
                    .append(")")
                    .append(":")
                    .append(message)
                    .append(", ");
        });
        return outMessage.deleteCharAt(outMessage.length() - 2).append("`").toString();
    }


    private static String getFieldName(ParameterNameDiscoverer parameterNameDiscoverer, Method method,
                                       ConstraintViolation constraintViolation) {
        String fieldName = "";
        for (Path.Node node : constraintViolation.getPropertyPath()) {
            if (node.getKind() == ElementKind.PARAMETER) {
                fieldName = getParameterName(node, method, parameterNameDiscoverer);
            } else if (node.getKind() == ElementKind.PROPERTY) {
                fieldName = node.getName();
            } else if (node.getKind() == ElementKind.RETURN_VALUE) {
                fieldName = node.getName();
            }
        }
        return fieldName;
    }

    private static String getParameterName(Path.Node node, Method method,
                                           ParameterNameDiscoverer parameterNameDiscoverer) {
        if (Objects.isNull(parameterNameDiscoverer) || Objects.isNull(method)) {
            return node.getName();
        } else {
            int index = node.as(Path.ParameterNode.class).getParameterIndex();
            String[] names = parameterNameDiscoverer.getParameterNames(method);
            return names == null ? node.getName() : names[index];
        }
    }

}
