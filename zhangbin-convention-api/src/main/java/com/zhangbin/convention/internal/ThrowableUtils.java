package com.zhangbin.convention.internal;

/**
 * @author zhangbin
 * @Type ThrowableUtils
 * @Desc 异常工具类
 * @date 2018-10-21
 * @Version V1.0
 */
public class ThrowableUtils {

    private static final int DEFAULT_ROW_COUNT = 10;

    /**
     * 获得异常的stack信息
     *
     * @param throwable 异常
     * @return string型的异常stack信息
     */
    public static String getStackTrace(Throwable throwable) {
        return getStackTrace(throwable, DEFAULT_ROW_COUNT);
    }

    /**
     * 获得异常的stack信息
     *
     * @param throwable 异常
     * @param rowCount 返回对应的堆栈行数
     * @return
     */
    private static String getStackTrace(Throwable throwable, int rowCount) {
        if (throwable == null) {
            return null;
        }
        Throwable cause = throwable;
        StackTraceElement[] trace;
        StringBuilder sb = new StringBuilder(cause.getClass().getName());
        // 追加 Message信息
        if (cause.getMessage() != null) {
            sb.append(":\t").append(cause.getMessage()).append("\n");
        } else {
            sb.append("\n");
        }
        for (int i = 0; i < rowCount && cause != null; i++) {
            if (i > 0) {
                sb.append("Caused by: ").append(cause.getClass().getName());
                // 追加 Message信息
                if (cause.getMessage() != null) {
                    sb.append(":\t").append(cause.getMessage()).append("\n");
                } else {
                    sb.append("\n");
                }
            }
            trace = cause.getStackTrace();
            for (StackTraceElement ste : trace) {
                sb.append("\tat ").append(ste).append("\n");
            }
            cause = cause.getCause();
        }
        return sb.toString();
    }

}
