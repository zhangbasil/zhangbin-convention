package com.zhangbin.convention.metrics.resultcode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbin
 * @Type ResultCodeObject
 * @Desc
 * @date 2018-11-14
 * @Version V1.0
 */
public class ResultCodeObject implements Serializable {

    private String className;
    private long lastModified;
    private List<CodeMessage> codeMessages = new ArrayList<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public List<CodeMessage> getCodeMessages() {
        return codeMessages;
    }

    public void setCodeMessages(List<CodeMessage> codeMessages) {
        this.codeMessages = codeMessages;
    }

    public void addCodeMessage(String code, String message) {
        CodeMessage codeMessage = new CodeMessage();
        codeMessage.setCode(code);
        codeMessage.setMessage(message);
        codeMessages.add(codeMessage);
    }

    public static class CodeMessage implements Serializable {

        private static final long serialVersionUID = 4074049052740820423L;

        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "CodeMessage{" + "resultcode='" + code + '\'' + ", message='" + message + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "ResultCodeObject{" + "className='" + className + '\'' + ", lastModified=" + lastModified
                + ", codeMessages=" + codeMessages + '}';
    }

}
