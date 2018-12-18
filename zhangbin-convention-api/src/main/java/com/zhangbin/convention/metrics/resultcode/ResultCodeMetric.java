package com.zhangbin.convention.metrics.resultcode;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangbin
 * @Type ResultCodeMetric
 * @Desc
 * @date 2018-11-12
 * @Version V1.0
 */
public class ResultCodeMetric implements Serializable {

    private String application;

    private String hostAddress;

    private List<ResultCodeObject> resultCodeObjects;

    public ResultCodeMetric() {

    }

    public ResultCodeMetric(String application, List<ResultCodeObject> resultCodeObjects) {
        this.application = application;
        this.resultCodeObjects = resultCodeObjects;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public List<ResultCodeObject> getResultCodeObjects() {
        return resultCodeObjects;
    }

    public void setResultCodeObjects(List<ResultCodeObject> resultCodeObjects) {
        this.resultCodeObjects = resultCodeObjects;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    @Override
    public String toString() {
        return "ResultCodeMetric{" + "application='" + application + '\'' + ", hostAddress='" + hostAddress + '\''
                + ", resultCodeObjects=" + resultCodeObjects + '}';
    }
}
