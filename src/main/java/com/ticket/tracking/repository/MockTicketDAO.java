package com.ticket.tracking.repository;

import com.ticket.tracking.entity.Ticket;
import com.ticket.tracking.parameter.TicketQueryParameter;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/*
資料持久層：擔任與資料庫溝通的媒介，會被Service呼叫。常透過「資料存取物件」（data access object，DAO）來實現。
Data Persistence Layer:
    Acts as a medium for communication with the database and will be called by Service.
    It is often achieved through "data access object" (DAO).
 */

@Repository
public class MockTicketDAO {

    private final List<Ticket> ticketDB = new ArrayList<>();

    public Ticket insert(Ticket ticket) {
        ticketDB.add(ticket);
        return ticket;
    }

    public Ticket replace(String id, Ticket ticket) {
        Optional<Ticket> ticketOp = find(id);
        ticketOp.ifPresent(p -> {
            p.setSummary(ticket.getSummary());
            p.setPriority(ticket.getPriority());
        });

        return ticket;
    }

    public void delete(String id) {
        ticketDB.removeIf(p -> p.getId().equals(id));
    }

    public Optional<Ticket> find(String id) {
        return ticketDB.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Ticket> find(TicketQueryParameter param) {
        String nameKeyword = Optional.ofNullable(param.getKeyword()).orElse("");
        String orderBy = param.getOrderBy();
        String sortRule = param.getSortRule();

        Comparator<Ticket> comparator = Objects.nonNull(orderBy) && Objects.nonNull(sortRule)
                ? configureSortComparator(orderBy, sortRule)
                : (p1, p2) -> 0;

        return ticketDB.stream()
                .filter(p -> p.getSummary().contains(nameKeyword))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private Comparator<Ticket> configureSortComparator(String orderBy, String sortRule) {
        Comparator<Ticket> comparator = (p1, p2) -> 0;

        if (orderBy.equalsIgnoreCase("priority")) {
            comparator = Comparator.comparing(Ticket::getPriority);
        } else if (orderBy.equalsIgnoreCase("summary")) {
            comparator = Comparator.comparing(Ticket::getSummary);
        }

        if (sortRule.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }
}
