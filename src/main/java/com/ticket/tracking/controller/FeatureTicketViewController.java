package com.ticket.tracking.controller;

import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.entity.ticket.Ticket;
import com.ticket.tracking.entity.ticket.TicketRequest;
import com.ticket.tracking.entity.ticket.TicketResponse;
import com.ticket.tracking.parameter.TicketQueryParameter;
import com.ticket.tracking.repository.FeatureTypeRepository;
import com.ticket.tracking.service.FeatureService;
import com.ticket.tracking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pm_dashboard",produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureTicketViewController {
    @Autowired
    private FeatureService featureService;

    @Autowired
    private TicketService ticketService;


    @GetMapping
    public ModelAndView featureTickets() {
        ModelAndView modelAndView = new ModelAndView("pm_dashboard");
        System.out.println("dashboard page");
        List<FeatureType> featureTypes = featureService.findFeatureTicket();
        modelAndView.addObject("tickets", featureTypes);
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("create page");
        /*
        setViewName -> {name}.html, file name
         */
        modelAndView.setViewName("qa/create");
        return modelAndView;
    }


    @PostMapping(value = "/create")
    public ModelAndView createFeatureTicket(FeatureType featureType) {
        System.out.println("create ticket page");
        ModelAndView modelAndView = new ModelAndView();
        featureService.createFeatureTicket(featureType);

        List<FeatureType> featureTypes = featureService.findFeatureTicket();
        modelAndView.addObject("tickets", featureType);
        modelAndView.setViewName("pm_dashboard");
        return modelAndView;
    }
}
