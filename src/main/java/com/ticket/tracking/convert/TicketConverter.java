package com.ticket.tracking.convert;

import com.ticket.tracking.entity.ticket.Ticket;
import com.ticket.tracking.entity.ticket.TicketRequest;
import com.ticket.tracking.entity.ticket.TicketResponse;
import com.ticket.tracking.exception.InvalidValueException;

public class TicketConverter {
    private TicketConverter() {}

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

    public static Ticket toTicket(TicketRequest request) {
        Ticket ticket = ticketObj(request);
//
//        if (ticket.getTicketType().equals("invalid")) {
//            throw new InvalidValueException("Invalid ticket type");
//        }
//        if (ticket.getPriority().equals("invalid")) {
//            throw new InvalidValueException("Invalid priority");
//        }
//        if (ticket.getSeverity().equals("invalid")) {
//            throw new InvalidValueException("Invalid severity");
//        }
//        if (ticket.getTicketStatus().equals("invalid")) {
//            throw new InvalidValueException("Invalid ticket status");
//        }

        return ticket;

    }

    private static Ticket ticketObj(TicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setSummary(request.getSummary());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setSeverity(request.getSeverity());
        ticket.setTicketStatus(request.getTicketStatus());
        ticket.setTicketType(request.getTicketType());
        ticket.setCreateDate(request.getCreateDate());
        ticket.setResolveDate(request.getResolveDate());
        ticket.setAssignee(request.getAssignee());
        ticket.setReporter(request.getReporter());

        return ticket;
    }

}
