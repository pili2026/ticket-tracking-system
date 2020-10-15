package com.ticket.tracking.exception;

import com.ticket.tracking.entity.TicketType;

import java.util.Arrays;

public class Validator {

    public void validator(TicketType request) {
        String[] ticketTypes = {"Bug", "Feature", "TestCase"};
        boolean typeValidator = Arrays.asList(ticketTypes).contains(request.getTicketType());

        String[] ticketSeverity = {"Critical", "Major", "Moderate", "Minor", "Cosmetic"};
        boolean severityValidator = Arrays.asList(ticketSeverity).contains(request.getSeverity());

        String[] ticketPriority = {"High", "Medium", "Low"};
        boolean priorityValidator = Arrays.asList(ticketPriority).contains(request.getPriority());

        String[] ticketStatus = {"New", "Start", "Resolved", "Close"};
        boolean statusValidator = Arrays.asList(ticketStatus).contains(request.getTicketStatus());


        if (!typeValidator) {
            throw new InvalidValueException("Invalid ticket type");
        }
        if (!statusValidator) {
            throw new InvalidValueException("Invalid ticket status");
        }
        if (!severityValidator) {
            throw new InvalidValueException("Invalid severity");
        }
        if (!priorityValidator) {
            throw new InvalidValueException("Invalid ticket status");
        }

    }
}
