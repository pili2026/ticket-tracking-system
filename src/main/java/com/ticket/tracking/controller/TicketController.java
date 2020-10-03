package com.ticket.tracking.controller;

import com.ticket.tracking.entity.Ticket;
import com.ticket.tracking.parameter.TicketQueryParameter;
import com.ticket.tracking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

/*
表示層：也就是Controller，應該負責接收前端的請求（request），並請Service處理，最後將資料回傳（response）。
(Presentation layer:
    that is, the Controller, which should be responsible for receiving the front-end request (request),
    asking the Service to process it, and finally returning the data (response).)
 */

@RestController
@RequestMapping(value = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {
    @Autowired
    private TicketService ticketService;


    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable("id") String id) {
        Ticket ticket = ticketService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getTickets(@ModelAttribute TicketQueryParameter param) {
        List<Ticket> tickets = ticketService.getTickets(param);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket request) {
        Ticket ticket = ticketService.createProduct(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticket.getId())
                .toUri();

        return ResponseEntity.created(location).body(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> replaceTicket(
            @PathVariable("id") String id, @RequestBody Ticket request) {
        Ticket ticket = ticketService.replaceTicket(id, request);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") String id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
