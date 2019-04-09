package com.zhangbin.convention.aop.exception;

import com.zhangbin.convention.code.CommonCode;
import com.zhangbin.convention.code.ErrorCode;
import com.zhangbin.convention.exception.ServiceErrorException;
import com.zhangbin.convention.exception.ServiceException;
import com.zhangbin.convention.exception.ServiceValidException;
import com.zhangbin.convention.internal.util.ValidationUtils;
import com.zhangbin.convention.result.Result;
import com.zhangbin.convention.result.Results;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhangbin
 * @Type ExceptionAspect
 * @Desc 异常处理
 * @date 2018-10-21
 * @Version V1.0
 */
@Aspect
public class ExceptionAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);

    private static final String EMPTY = "";
    private static final String SPLITTER = ",";
    private static final String DOT = ".";

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Autowired(required = false)
    private ExternalExceptionHandler externalExceptionHandler;

    @Autowired(required = false)
    private ExceptionAlarmHandler exceptionAlarmHandler;

    /**
     *
     * 所有返回值为Result的子类的方法
     *
     * 排除 com.zhangbin.convention.aop.exception.ExternalExceptionHandler
     *
     */
    @Pointcut("execution(com.zhangbin.convention.result.Result + *..*.*(..)) && " +
            "!execution(* com.zhangbin.convention.aop.exception.ExternalExceptionHandler.*(..))")
    private void anyResult() {

    }

    @Around("anyResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        boolean isResultClass = Result.class == method.getReturnType()
                || Result.class.isAssignableFrom(method.getReturnType());

        Object[] args = joinPoint.getArgs();
        try {
            Object returnValue = joinPoint.proceed();
            if (Objects.isNull(returnValue)) {
                throw new ServiceErrorException(ErrorCode.RETURN_NULL_ERROR);
            }
            return returnValue;
        } catch (ConstraintViolationException violationException) {
            List<Result.ViolationItem> violationItems = ValidationUtils.convertToResultViolationItems(
                    violationException.getConstraintViolations(), parameterNameDiscoverer, method);
            if (isResultClass) {
                return Results.invalid(violationItems);
            }
            return getResultValue(method, violationException)
                    .setCode(CommonCode.INVALID_ARGS.code())
                    .setMessage(CommonCode.INVALID_ARGS.message())
                    .setViolationItems(violationItems);
        } catch (ServiceValidException validException) {
            if (isResultClass) {
                if (!CollectionUtils.isEmpty(validException.getViolationItems())) {
                    return Results.invalid(validException.getMessage(), validException.getViolationItems());
                } else {
                    return Results.invalid(validException.getMessage());
                }
            }
            return getResultValue(method, validException)
                    .setCode(CommonCode.INVALID_ARGS.code())
                    .setMessage(CommonCode.INVALID_ARGS.message())
                    .setViolationItems(validException.getViolationItems());
        } catch (ServiceException serviceException) {
            if (isResultClass) {
                return Results.failure(serviceException);
            }
            return getResultValue(method, serviceException)
                    .setCode(serviceException.getCode())
                    .setMessage(serviceException.getMessage());
        } catch (ServiceErrorException errorException) {
            LOGGER.error("code:" + errorException.getCode() + " msg:" + errorException.getMessage(), errorException);
            if (isResultClass) {
                return Results.error(errorException);
            }
            return getResultValue(method, errorException)
                    .setCode(errorException.getCode())
                    .setMessage(errorException.getMessage());
        } catch (Throwable throwable) {// 未知系统异常
            LOGGER.error(throwable.getMessage(), throwable);
            // 未知系统异常报警通知（邮件和短信通知）异步
            if (Objects.nonNull(exceptionAlarmHandler)) {
                try {
                    CompletableFuture.runAsync(() -> exceptionAlarmHandler.notice(throwable, method, args));
                } catch (Throwable t) {
                    LOGGER.error("未知系统异常->发送报警通知异常：" + t.getMessage(), t);
                }
            }

            // 除约定以外的其他扩展异常
            if (Objects.nonNull(externalExceptionHandler)) {
                try {
                    Result handle = externalExceptionHandler.handle(throwable, method, args);
                    if (Objects.nonNull(handle)) {
                        return handle;
                    }
                } catch (Throwable t) {
                    LOGGER.error("未知系统异常->除约定以外的其他扩展异常：" + t.getMessage(), t);
                }
            }
            // 默认未知系统异常处理
            if (isResultClass) {
                return Results.error();
            }
            return getResultValue(method, throwable)
                    .setCode(ErrorCode.UNKNOWN_ERROR.code())
                    .setMessage(ErrorCode.UNKNOWN_ERROR.message());
        }

    }

    private Result getResultValue(Method method, Throwable throwable) throws Throwable {
        try {
            return (Result) method.getReturnType().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            LOGGER.error("method:" + buildOperationName(method) + " " + e.getMessage(), e);
            throw throwable;
        }
    }

    private String buildOperationName(Method method) {
        String args = join(parameterNameDiscoverer.getParameterNames(method));
        return method.getDeclaringClass().getSimpleName() + DOT + method.getName() + "(" + args + ")";
    }

    private String join(String[] params) {
        StringBuilder sb = new StringBuilder(EMPTY);
        if (params == null || params.length == 0) {
            return EMPTY;
        }
        for (String param : params) {
            sb.append(param).append(SPLITTER);
        }
        return sb.substring(0, sb.length() - 1);
    }

}
