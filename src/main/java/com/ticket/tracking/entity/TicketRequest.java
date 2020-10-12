package com.ticket.tracking.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class TicketRequest {

    @NotEmpty(message = "Summary is undefined.")
    private String summary;

    @NotEmpty(message = "Description is undefined.")
    private String description;

    @NotEmpty(message = "Ticket status is undefined.")
    private String ticketStatus;

    @NotEmpty(message = "Ticket type is undefined.")
    private String ticketType;

    @NotEmpty(message = "Priority is undefined.")
    private String priority;

    @NotEmpty(message = "Severity is undefined.")
    private String severity;

    private String createDate;

    private String resolveDate;

    @NotEmpty(message = "Reporter is undefined.")
    private String reporter;

    @NotEmpty(message = "Assignee is undefined.")
    private String assignee;

    public TicketRequest() {}


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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(String resolveDate) {
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

