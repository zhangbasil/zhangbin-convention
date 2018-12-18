package com.zhangbin.convention.domain;

import java.io.Serializable;

/**
 * @author zhangbin
 * @Type PageQuery
 * @Desc 统一分页查询父类
 * @date 2018-11-12
 * @Version V1.0
 */
public class PageQuery implements Serializable {

    /**
     * 默认每页显示的记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 最大每页显示的记录数
     */
    public static final int MAX_PAGE_SIZE = 500;
    /**
     * 默认页数
     */
    public static final int DEFAULT_PAGE_NUMBER = 1;


    public int pageNumber;

    public int pageSize;


    public void setPageNumber(int pageNumber) {
        if (pageNumber < 1) {
            throw new IllegalArgumentException("Page number must not be less than one!");
        }
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
    }

    public PageQuery() {
        this(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
    }

    public PageQuery(int pageNumber, int pageSize) {
        this.setPageNumber(pageNumber);
        this.setPageSize(pageSize);
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return (pageNumber - 1) * pageSize;
    }
}
