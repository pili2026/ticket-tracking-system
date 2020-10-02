package com.ticket.tracking.service;

import com.ticket.tracking.entity.Ticket;
import com.ticket.tracking.exception.ConflictException;
import com.ticket.tracking.exception.NotFoundException;
import com.ticket.tracking.parameter.TicketQueryParameter;
import com.ticket.tracking.repository.MockTicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private MockTicketDAO ticketDAO;

    public Ticket createProduct(Ticket request) {
        boolean isIdDuplicated = ticketDAO.find(request.getId()).isPresent();
        if (isIdDuplicated) {
            throw new ConflictException("The id of the product is duplicated.");
        }

        Ticket product = new Ticket();
        product.setId(request.getId());
        product.setSummary(request.getSummary());
        product.setPriority(request.getPriority());

        return ticketDAO.insert(product);
    }

    public Ticket getTicket(String id) {
        return ticketDAO.find(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));
    }

    public Ticket replaceTicket(String id, Ticket request) {
        Ticket ticket = getTicket(id);
        return ticketDAO.replace(ticket.getId(), request);
    }

    public void deleteTicket(String id) {
        Ticket ticket = getTicket(id);
        ticketDAO.delete(ticket.getId());
    }

    public List<Ticket> getTickets(TicketQueryParameter param) {
        return ticketDAO.find(param);
    }
}
