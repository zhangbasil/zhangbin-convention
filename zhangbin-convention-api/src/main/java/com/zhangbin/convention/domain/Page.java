package com.zhangbin.convention.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangbin
 * @Type Page
 * @Desc 返回统一分页接口
 * @date 2018-11-12
 * @Version V1.0
 */
public interface Page<T> extends Serializable {

    /**
     * 获取页号
     * 
     * @return 页号
     */
    int getPageNumber();

    /**
     * 获取每页可显示的记录数
     * 
     * @return 每页可显示的记录数
     */
    int getPageSize();

    /**
     * 获取总记录数
     * 
     * @return 总记录数
     */
    long getTotalCount();

    /**
     * 获取数据列表
     * 
     * @return 数据列表
     */
    List<T> getResults();

    /**
     * 获取总页数
     * 
     * @return 总页数
     */
    int getTotalPages();

}
