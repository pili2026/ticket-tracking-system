package com.ticket.tracking.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
public class FeatureType {

    private String id;
    private String summary;
    private String description;
    private String ticketStatus;
    private String ticketType;
    private String priority;
    private String severity;
    private long createDate;
    private long expectedDate;
    private long resolveDate;
    private String reporter;
    private String assignee;


    public FeatureType() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(long expectedDate) {
        this.expectedDate = expectedDate;
    }

    public long getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(long resolveDate) {
        this.resolveDate = resolveDate;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
