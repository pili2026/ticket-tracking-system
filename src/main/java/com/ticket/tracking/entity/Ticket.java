package com.ticket.tracking.entity;

// define ticket object
public class Ticket {
    private String id;
    private String summary;
    private int priority;

    public Ticket() {

    }

    public Ticket(String id, String summary, int priority) {
        this.id = id;
        this.summary = summary;
        this.priority = priority;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
