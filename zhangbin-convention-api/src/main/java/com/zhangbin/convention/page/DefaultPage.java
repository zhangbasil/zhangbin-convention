package com.zhangbin.convention.page;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangbin
 * @Type DefaultPage
 * @Desc 默认分页对象
 * @date 2018-11-12
 * @Version V1.0
 */
public class DefaultPage<T> implements Page<T> {

    /**
     * 当前页
     */
    private int pageNumber;

    /**
     * 每页的大小
     */
    private int pageSize;

    /**
     * 总页
     */
    private int totalPages;
    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 结果集合
     */
    private List<T> results;

    public DefaultPage() {
        this(PageQuery.DEFAULT_PAGE_NUMBER, PageQuery.DEFAULT_PAGE_SIZE, 0, Collections.emptyList());
    }

    public DefaultPage(int pageNumber, int pageSize, long totalCount, List<T> results) {
        this.setPageNumber(pageNumber);
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
        this.setResults(results);
        // 计算总页数
        this.totalPages = (pageSize == 0 || totalCount == 0) ? 1
                : (int) Math.ceil((double) totalCount / (double) this.pageSize);
    }

    public DefaultPage(PageQuery pageQuery, long totalCount, List<T> results) {
        this(pageQuery.getPageNumber(), pageQuery.getPageSize(), totalCount, results);
    }

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
        this.pageSize = pageSize > PageQuery.MAX_PAGE_SIZE ? PageQuery.MAX_PAGE_SIZE : pageSize;
    }

    public void setTotalCount(long totalCount) {
        if (totalCount < 0) {
            throw new IllegalArgumentException("Total count must not be less than zero!");
        }
        this.totalCount = totalCount;
    }

    public void setResults(List<T> results) {
        this.results = Objects.nonNull(results) ? results : Collections.emptyList();
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getTotalCount() {
        return totalCount;
    }

    @Override
    public List<T> getResults() {
        return results;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }
}
