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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    public TicketResponse getTicketResponse(String id) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find ticket"));
        return TicketConverter.toTicketResponse(ticket);
    }

    public List<TicketResponse> getTicketResponses(TicketQueryParameter param) {
        String nameKeyword = Optional.ofNullable(param.getKeyword()).orElse("");
        int priceFrom = Optional.ofNullable(param.getCreateDateFrom()).orElse(0);
        int priceTo = Optional.ofNullable(param.getCreateDateTo()).orElse(Integer.MAX_VALUE);
        Sort sort = configureSort(param.getOrderBy(), param.getSortRule());

        List<Ticket> products = repository.findByCreateDateToBetweenAndSummaryLikeIgnoreCase(priceFrom, priceTo, nameKeyword, sort);

        return products.stream()
                .map(TicketConverter::toTicketResponse)
                .collect(Collectors.toList());
    }

    public TicketResponse createTicket(TicketRequest request) {
        Ticket ticket = ticketObj(request);
        if (ticket.getTicketType().equals("invalid")) {
            throw new InvalidValueException("Invalid ticket type");
        }
        if (ticket.getPriority().equals("invalid")) {
            throw new InvalidValueException("Invalid priority");
        }
        if (ticket.getSeverity().equals("invalid")) {
            throw new InvalidValueException("Invalid severity");
        }
        if (ticket.getTicketStatus().equals("invalid")) {
            throw new InvalidValueException("Invalid ticket status");
        }
        repository.insert(ticket);

        return TicketConverter.toTicketResponse(ticket);
    }

    public TicketResponse replaceTicket(String id, TicketRequest request) {
        Ticket oldTicket = getTicket(id);
        Ticket newTicket = TicketConverter.toTicket(request);
        newTicket.setId(oldTicket.getId());
        repository.save(newTicket);

        return TicketConverter.toTicketResponse(newTicket);
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

    private Sort configureSort(String orderBy, String sortRule) {
        Sort sort = Sort.unsorted();
        if (Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
            Sort.Direction direction = Sort.Direction.fromString(sortRule);
            sort = new Sort(direction, orderBy);
        }

        return sort;
    }

    private Ticket ticketObj(TicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setSummary(request.getSummary());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setSeverity(request.getSeverity());
        ticket.setTicketStatus(request.getTicketStatus());
        ticket.setTicketType(request.getTicketType());
        ticket.setCreateDate(request.getCreateDate());
        ticket.setExpectedDate(request.getExpectedDate());
        ticket.setResolveDate(request.getResolveDate());
        ticket.setAssignee(request.getAssignee());
        ticket.setReporter(request.getReporter());

        return ticket;
    }
}
