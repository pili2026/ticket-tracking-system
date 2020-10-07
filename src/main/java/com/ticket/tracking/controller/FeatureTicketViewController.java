package com.ticket.tracking.controller;

import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.repository.FeatureTypeRepository;
import com.ticket.tracking.service.FeatureService;
import com.ticket.tracking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureTicketViewController {
    @Autowired
    private FeatureService featureService;

    @GetMapping(value = "/feature")
    public ModelAndView createFeatureTicket() {
        ModelAndView modelAndView = new ModelAndView();
        FeatureType featureType = new FeatureType();
        modelAndView.addObject("feature", featureType);
        modelAndView.setViewName("feature");
        return modelAndView;
    }

    @PostMapping(value = "/feature")
    public ModelAndView createNewUser(FeatureType featureType, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        featureService.createFeatureTicket(featureType);
        modelAndView.addObject("successMessage", "User has been added successfully");
        modelAndView.addObject("feature", new FeatureType());
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
