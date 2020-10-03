package com.ticket.tracking.parameter;

public class TicketQueryParameter {
    private String keyword;
    private Integer priorityFrom;
    private Integer priorityTo;
    private String orderBy;
    private String sortRule;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPriorityFrom() {
        return priorityFrom;
    }

    public void setPriorityFrom(Integer priorityFrom) {
        this.priorityFrom = priorityFrom;
    }

    public Integer getPriorityTo() {
        return priorityTo;
    }

    public void setPriorityTo(Integer priorityTo) {
        this.priorityTo = priorityTo;
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
