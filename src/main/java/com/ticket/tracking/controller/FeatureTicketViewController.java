package com.ticket.tracking.controller;

import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.entity.ticket.TicketResponse;
import com.ticket.tracking.parameter.TicketQueryParameter;
import com.ticket.tracking.repository.FeatureTypeRepository;
import com.ticket.tracking.service.FeatureService;
import com.ticket.tracking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/pm_dashboard",produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureTicketViewController {
    @Autowired
    private FeatureService featureService;


    @GetMapping("/create")
    public ModelAndView featureTicket() {
        ModelAndView modelAndView = new ModelAndView();
        FeatureType featureType = new FeatureType();
        modelAndView.addObject("feature", featureType);
        System.out.println("create_ticket");
        modelAndView.setViewName("create");
        return modelAndView;
    }


    @PostMapping(value = "/create/feature")
    public ModelAndView createFeatureTicket(FeatureType featureType, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        featureService.createFeatureTicket(featureType);
        modelAndView.addObject("successMessage", "User has been added successfully");
        modelAndView.addObject("feature", new FeatureType());
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
