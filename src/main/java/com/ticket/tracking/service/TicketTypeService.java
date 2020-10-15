package com.ticket.tracking.service;

import com.ticket.tracking.entity.TicketType;
import com.ticket.tracking.exception.Validator;
import com.ticket.tracking.function.TimeFunc;
import com.ticket.tracking.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketTypeService {


    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    public List<TicketType> getTicketsByType(String type) {
        List<TicketType> ticketTypes = ticketTypeRepository.findByTicketTypeLikeIgnoreCase(type);
        return new ArrayList<>(ticketTypes);
    }

    public TicketType getTicketById(String id) {
        Optional<TicketType> ticketType = ticketTypeRepository.findById(id);
        TicketType ticket = null;

        if (ticketType.isPresent()) {
            ticket = ticketType.get();
        } else {
            throw new RuntimeException("Feature ticket not found for id ::" + id);
        }
        return ticket;
    }

    public void createTicket(TicketType ticketType, String name, String type) {
        TimeFunc timeFunc = new TimeFunc();
        Validator validator = new Validator();
        validator.validator(ticketType);

        ticketType.setSummary(ticketType.getSummary());
        ticketType.setDescription(ticketType.getDescription());
        ticketType.setPriority(ticketType.getPriority());
        ticketType.setSeverity(ticketType.getSeverity());
        ticketType.setTicketStatus(ticketType.getTicketStatus());
        ticketType.setTicketType(type);
        ticketType.setCreateDate(timeFunc.localDateTime());
        ticketType.setUpdatedDate(timeFunc.localDateTime());
        ticketType.setAssignee(ticketType.getAssignee());
        ticketType.setReporter(name);
        ticketTypeRepository.save(ticketType);

    }

    public void updateTicket(TicketType ticketType) {
        TimeFunc timeFunc = new TimeFunc();

        TicketType oldTicket = ticketTypeRepository.findByTicketTypeByIdLikeIgnoreCase(ticketType.getTicketType(), ticketType.getId());

        ticketType.setSummary(ticketType.getSummary());
        ticketType.setDescription(ticketType.getDescription());
        ticketType.setPriority(oldTicket.getPriority());
        ticketType.setSeverity(oldTicket.getSeverity());
        ticketType.setTicketStatus(ticketType.getTicketStatus());
        ticketType.setTicketType(oldTicket.getTicketType());
        ticketType.setCreateDate(oldTicket.getCreateDate());
        ticketType.setUpdatedDate(timeFunc.localDateTime());
        ticketType.setAssignee(oldTicket.getAssignee());
        ticketType.setReporter(oldTicket.getReporter());
        ticketTypeRepository.save(ticketType);

    }

    public void deleteTicket(String id) {
        ticketTypeRepository.deleteById(id);
    }
}
