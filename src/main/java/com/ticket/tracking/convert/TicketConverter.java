package com.ticket.tracking.convert;

import com.ticket.tracking.entity.Ticket;
import com.ticket.tracking.entity.TicketRequest;
import com.ticket.tracking.entity.TicketResponse;
import com.ticket.tracking.exception.InvalidValueException;
import com.ticket.tracking.exception.Validator;

public class TicketConverter {
    private TicketConverter() {
    }

    public static TicketResponse toTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setSummary(ticket.getSummary());
        response.setDescription(ticket.getDescription());
        response.setPriority(ticket.getPriority());
        response.setSeverity(ticket.getSeverity());
        response.setTicketStatus(ticket.getTicketStatus());
        response.setTicketType(ticket.getTicketType());
        response.setCreateDate(ticket.getCreateDate());
        response.setResolveDate(ticket.getResolveDate());
        response.setAssignee(ticket.getAssignee());
        response.setReporter(ticket.getReporter());

        return response;
    }

    public static Ticket toTicket(String type, TicketRequest request) {
        Validator validator = new Validator();
        validator.validator(request);
        return ticketObj(type, request);

    }

    private static Ticket ticketObj(String type, TicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setSummary(request.getSummary());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setSeverity(request.getSeverity());
        ticket.setTicketStatus(request.getTicketStatus());
        ticket.setTicketType(type);
        ticket.setCreateDate(request.getCreateDate());
        ticket.setResolveDate(request.getResolveDate());
        ticket.setAssignee(request.getAssignee());
        ticket.setReporter(request.getReporter());

        return ticket;
    }
}