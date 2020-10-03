package com.ticket.tracking.entity;

public enum TicketType {
    Bug("Bug"),
    Feature("Feature"),
    TestCase("TestCase");

    private final String text;

    TicketType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
