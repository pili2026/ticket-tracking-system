package com.ticket.tracking.service;

import com.ticket.tracking.convert.TicketConverter;
import com.ticket.tracking.entity.Ticket;
import com.ticket.tracking.entity.TicketRequest;
import com.ticket.tracking.entity.TicketResponse;
import com.ticket.tracking.exception.InvalidValueException;
import com.ticket.tracking.exception.NotFoundException;
import com.ticket.tracking.parameter.TicketQueryParameter;
import com.ticket.tracking.repository.TickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/*
業務邏輯層：又稱作Service，會被Controller呼叫。它負責根據請求來進行資料處理，並回傳結果。也可能被其他Service呼叫。
Business logic layer:
    Also called Service, it will be called by Controller.
    It is responsible for processing data according to the request and returning the results.
    It may also be called by other services.
 */
@Service
public class TicketService {
    @Autowired
    private TickRepository repository;

    public Ticket getTicket(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find ticket."));
    }

    // get all ticket, regardless of category
    public List<TicketResponse> getTicketResponses() {

        List<Ticket> tickets = repository.findAll();
        return tickets.stream()
                .map(TicketConverter::toTicketResponse)
                .collect(Collectors.toList());
    }


    public void deleteTicket(String id) {
        repository.deleteById(id);
    }

    public List<Ticket> getTickets(TicketQueryParameter param) {
        String nameKeyword = Optional.ofNullable(param.getKeyword()).orElse("");
        int createDateFrom = Optional.ofNullable(param.getCreateDateFrom()).orElse(0);
        int createDateTo = Optional.ofNullable(param.getCreateDateTo()).orElse(Integer.MAX_VALUE);

        Sort sort = configureSort(param.getOrderBy(), param.getSortRule());

        return repository.findByCreateDateToBetweenAndSummaryLikeIgnoreCase(createDateFrom, createDateTo, nameKeyword, sort);
    }


    /*  ----with ticket type and id----  */
    // updated ticket with specified type and ID
    public TicketResponse replaceTicketTypeById(String type, String id, TicketRequest request) {
        Ticket oldTicket = repository.findByTicketTypeByIdLikeIgnoreCase(type, id);
        Ticket newTicket = TicketConverter.toTicket(request);
        newTicket.setId(oldTicket.getId());
        repository.save(newTicket);

        return TicketConverter.toTicketResponse(newTicket);
    }

    // get ticket with specified type and ID
    public TicketResponse getTicketResponsesByTypeId(String type, String id) {
        Ticket tickets = repository.findByTicketTypeByIdLikeIgnoreCase(type, id);

        return TicketConverter.toTicketResponse(tickets);
    }

    /*  ----with ticket type----  */
    // get same tickets
    public List<TicketResponse> getTicketResponsesByType(String type) {
        List<Ticket> tickets = repository.findByTicketTypeLikeIgnoreCase(type);

        return tickets.stream()
                .map(TicketConverter::toTicketResponse)
                .collect(Collectors.toList());
    }

    public TicketResponse createTicketByType(TicketRequest request) {
        validator(request);
        Ticket ticket = ticketObjByType(request);
        repository.insert(ticket);

        return TicketConverter.toTicketResponse(ticket);
    }

    /*  ----with ticket object----  */
    private Ticket ticketObjByType(TicketRequest request) {
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

    private Sort configureSort(String orderBy, String sortRule) {
        Sort sort = Sort.unsorted();
        if (Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
            Sort.Direction direction = Sort.Direction.fromString(sortRule);
            sort = new Sort(direction, orderBy);
        }

        return sort;
    }

    private void validator(TicketRequest request) {
        String[] ticketTypes = {"Bug", "Feature", "TestCase"};
        boolean typeValidator = Arrays.asList(ticketTypes).contains(request.getTicketType());

        String[] ticketSeverity = {"Critical", "Major", "Moderate", "Minor", "Cosmetic"};
        boolean severityValidator = Arrays.asList(ticketSeverity).contains(request.getSeverity());

        String[] ticketPriority = {"High", "Medium", "Low"};
        boolean priorityValidator = Arrays.asList(ticketPriority).contains(request.getPriority());

        if (!typeValidator) {
            throw new InvalidValueException("Invalid ticket type");
        }
        if (!request.getPriority().equals("New")) {
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
