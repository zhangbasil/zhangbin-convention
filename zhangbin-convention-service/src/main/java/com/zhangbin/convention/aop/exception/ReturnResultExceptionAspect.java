package com.zhangbin.convention.aop.exception;

import com.zhangbin.convention.code.CommonCode;
import com.zhangbin.convention.code.ErrorCode;
import com.zhangbin.convention.domain.Result;
import com.zhangbin.convention.domain.Results;
import com.zhangbin.convention.exception.ServiceErrorException;
import com.zhangbin.convention.exception.ServiceException;
import com.zhangbin.convention.exception.ServiceValidException;
import com.zhangbin.convention.internal.ThrowableUtils;
import com.zhangbin.convention.internal.util.ValidationUtils;
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
import java.util.Optional;

/**
 * @author zhangbin
 * @Type ReturnResultExceptionAspect
 * @Desc
 * @date 2018-10-21
 * @Version V1.0
 */
@Aspect
public class ReturnResultExceptionAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnResultExceptionAspect.class);

    private static final String EMPTY = "";
    private static final String SPLITTER = ",";
    private static final String DOT = ".";

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Autowired(required = false)
    private ReturnResultExceptionHandler returnResultExceptionHandler;

    /**
     * 所有返回值为Result的子类的方法
     */
    @Pointcut("execution(com.zhangbin.convention.domain.Result + *..*.*(..)) && !execution(* com.zhangbin.convention.aop.exception.ReturnResultExceptionHandler.*(..))")
    private void anyResult() {

    }

    @Around("anyResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        boolean isResultClass = Result.class == method.getReturnType()
                || Result.class.isAssignableFrom(method.getReturnType());

        try {
            Object returnValue = joinPoint.proceed();
            if (returnValue == null) {
                throw new ServiceErrorException(ErrorCode.RETURN_NULL_ERROR);
            }
            return returnValue;
        } catch (ConstraintViolationException violationException) {
            LOGGER.info("method:" + buildOperationName(method) + " " + violationException.getMessage());
            List<Result.ViolationItem> violationItems = ValidationUtils.convertToResultViolationItems(
                    violationException.getConstraintViolations(), parameterNameDiscoverer, method);
            if (isResultClass) {
                return Results.invalid(violationItems);
            }
            return getResultValue(method, violationException).setCode(CommonCode.INVALID_ARGS.code())
                    .setMessage(CommonCode.INVALID_ARGS.message()).setViolationItems(violationItems);
        } catch (ServiceValidException validException) {
            LOGGER.info("method:" + buildOperationName(method) + " " + validException.getDetailMessage());
            if (isResultClass) {
                if (!CollectionUtils.isEmpty(validException.getViolationItems())) {
                    return Results.invalid(validException.getMessage(), validException.getViolationItems());
                } else {
                    return Results.invalid(validException.getMessage());
                }
            }
            return getResultValue(method, validException).setCode(CommonCode.INVALID_ARGS.code())
                    .setMessage(CommonCode.INVALID_ARGS.message())
                    .setViolationItems(validException.getViolationItems());
        } catch (ServiceException serviceException) {
            LOGGER.info("method:" + buildOperationName(method) + " " + serviceException.getDetailMessage());
            if (isResultClass) {
                return Results.failure(serviceException);
            }
            return getResultValue(method, serviceException).setCode(serviceException.getCode())
                    .setMessage(serviceException.getMessage());
        } catch (ServiceErrorException errorException) {
            LOGGER.error("code:" + errorException.getCode() + " msg:" + errorException.getMessage(), errorException);
            if (isResultClass) {
                return Results.error(errorException);
            }
            return getResultValue(method, errorException).setCode(errorException.getCode())
                    .setMessage(errorException.getMessage()).setErrorClass(errorException.getClass().getName())
                    .setErrorStack(ThrowableUtils.getStackTrace(errorException));
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage(), throwable);
            // 先行执行自定义异常增强
            Optional<Result> handleResult = this.handleCustomException(throwable, method, joinPoint.getArgs());
            if (handleResult.isPresent()) {
                return handleResult.get();
            }
            if (isResultClass) {
                return Results.error(throwable);
            }
            return getResultValue(method, throwable).setCode(ErrorCode.UNKNOWN_ERROR.code())
                    .setMessage(ErrorCode.UNKNOWN_ERROR.message()).setErrorClass(throwable.getClass().getName())
                    .setErrorStack(ThrowableUtils.getStackTrace(throwable));
        }

    }

    private Optional<Result> handleCustomException(Throwable t, Method method, Object[] args) {
        if (Objects.isNull(returnResultExceptionHandler)) {
            return Optional.empty();
        }
        Result result = null;
        try {
            result = returnResultExceptionHandler.handleReturnResultException(t, method, args);
        } catch (Throwable throwable) {
            LOGGER.error("Return result exception advice occurrence of user exception", throwable);
        }
        return Optional.ofNullable(result);
    }

    private Result getResultValue(Method method, Throwable throwable) throws Throwable {
        try {
            return (Result) method.getReturnType().newInstance();
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
