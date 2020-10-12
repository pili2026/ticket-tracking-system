package com.ticket.tracking.entity.ticket;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @Min(value = 0, message = "Create date is invalid.")
    private int createDate;

    @Min(value = 0, message = "Resolve date is invalid.")
    private int resolveDate;

    @NotEmpty(message = "Reporter is undefined.")
    private String reporter;

    @NotEmpty(message = "Assignee is undefined.")
    private String assignee;


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
        String[] targetTypeArray = {"New", "Start", "Close", "Resolved", "Verify"};
        for (String s: targetTypeArray) {
            if (s.equals(ticketStatus)) {
                this.ticketStatus = ticketStatus;
            } else {
                this.ticketStatus = "invalid";
            }
            break;
        }
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        String[] targetTypeArray = {"Bug", "Feature", "TestCase"};
        for (String s: targetTypeArray) {
            if (s.equals(ticketType)) {
                this.ticketType = ticketType;
                break;
            } else {
                this.ticketType = "invalid";
            }
        }
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        String[] targetTypeArray = {"High", "Medium", "Low"};
        for (String s: targetTypeArray) {
            if (s.equals(priority)) {
                this.priority = priority;
                break;
            } else {
                this.priority = "invalid";
            }
            break;
        }

    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        String[] targetTypeArray = {"Critical", "Major", "Moderate", "Minor", "Cosmetic"};
        for (String s: targetTypeArray) {
            if (s.equals(severity)) {
                this.severity = severity;
            } else {
                this.severity = "invalid";
            }
            break;
        }
    }

    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
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
