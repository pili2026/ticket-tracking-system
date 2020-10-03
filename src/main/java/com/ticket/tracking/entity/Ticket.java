package com.ticket.tracking.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Document(collection = "tickets")
// define ticket object
public class Ticket {
    private String id;
    private String summary;
    private String description;
    private String ticketStatus;
    private String ticketType;
    private String priority;
    private String severity;
    private int createDate;
    private int expectedDate;
    private int resolveDate;
    private String reporter;
    private String assignee;


    public Ticket() {

    }

    public Ticket(String id, String summary, String description, String ticketStatus, String ticketType,
                  String priority, String severity, int createDate, int expectedDate, int resolveDate,
                  String reporter, String assignee) {
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.ticketStatus = ticketStatus;
        this.ticketType = ticketType;
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

    public void setTicketStatus(String ticketStatus) {
        String[] targetTypeArray = {"New", "Start", "Close", "Resolve", "Verify"};
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
