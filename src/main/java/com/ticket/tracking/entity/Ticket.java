package com.ticket.tracking.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
// define ticket object
public class Ticket {
    private String id;
    private String summary;
    private String description;
    private String ticketStatus;
    private String priority;
    private String severity;
    private int createDate;
    private int expectedDate;
    private int resolveDate;
    private String reporter;
    private String assignee;


    public Ticket() {

    }

    public Ticket(String id, String summary, String description, String ticketStatus, String priority, String severity,
                  int createDate, int expectedDate, int resolveDate, String reporter, String assignee) {
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.ticketStatus = ticketStatus;
        this.priority = priority;
        this.severity = severity;
        this.createDate = createDate;
        this.expectedDate = expectedDate;
        this.resolveDate = resolveDate;
        this.reporter = reporter;
        this.assignee = assignee;
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

    public void setTicketStatus(String priority) {
        this.ticketStatus = ticketStatus;
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

    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public int getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(int expectedDate) {
        this.expectedDate = expectedDate;
    }

    public int getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(int resolveDate) {
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
