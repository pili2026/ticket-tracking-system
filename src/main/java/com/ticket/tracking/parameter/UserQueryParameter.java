package com.ticket.tracking.parameter;

public class UserQueryParameter {
    private String keyword;
    private Integer createDateFrom;
    private Integer createDateTo;
    private String orderBy;
    private String sortRule;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCreateDateFrom() {
        return createDateFrom;
    }

    public void setCreateDateFrom(Integer createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    public Integer getCreateDateTo() {
        return createDateTo;
    }

    public void setCreateDateTo(Integer createDateTo) {
        this.createDateTo = createDateTo;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSortRule() {
        return sortRule;
    }

    public void setSortRule(String sortRule) {
        this.sortRule = sortRule;
    }
}
