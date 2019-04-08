package com.zhangbin.convention.base;

import java.time.LocalDateTime;

/**
 * @author zhangbin
 * @Type BaseDomain
 * @Desc
 * @date 2018-12-26
 * @Version V1.0
 */
public abstract class BaseDomain {

    // 主键Id
    private Long id;
    // 创建时间
    private LocalDateTime gmtCreated;
    // 最新修改时间
    private LocalDateTime gmtModified;
    // 逻辑删除
    private Boolean deleted = false;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
