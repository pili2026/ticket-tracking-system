package com.ticket.tracking.controller;

import com.ticket.tracking.entity.ticket.TicketRequest;
import com.ticket.tracking.entity.ticket.TicketResponse;
import com.ticket.tracking.parameter.TicketQueryParameter;
import com.ticket.tracking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getTickets(@ModelAttribute TicketQueryParameter param) {
        List<TicketResponse> tickets = ticketService.getTicketResponses(param);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<TicketResponse>> getTicketsByType(@PathVariable("type") String type) {
        List<TicketResponse> tickets = ticketService.getTicketResponsesByType(type);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<TicketResponse> getTicketByTypeId(@PathVariable("type") String type,
                                                            @PathVariable("id") String id) {

        TicketResponse ticket = ticketService.getTicketResponsesByTypeId(type, id);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/{type}")
    public ResponseEntity<TicketResponse> createTicketByType(@PathVariable("type") String type,
                                                       @Valid @RequestBody TicketRequest request) {
        TicketResponse ticket = ticketService.createTicketByType(type, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticket.getId())
                .toUri();

        return ResponseEntity.created(location).body(ticket);
    }

    @PutMapping("/{type}/{id}")
    public ResponseEntity<TicketResponse> replaceTicket(
            @PathVariable("type") String type, @PathVariable("id") String id,
            @Valid @RequestBody TicketRequest request) {
        TicketResponse ticket = ticketService.replaceTicketTypeById(type, id, request);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") String id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
