package com.ticket.tracking.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ticket.tracking.entity.TicketType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.service.CustomLoginUserDetailsService;
import com.ticket.tracking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PmViewController {

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private CustomLoginUserDetailsService customLoginUserDetailsService;


    @GetMapping("/pm_dashboard")
    public ModelAndView featureTickets() {
        ModelAndView modelAndView = new ModelAndView("pm_dashboard");
        List<TicketType> ticketTypes = ticketTypeService.getTicketsByType("Feature");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        modelAndView.addObject("tickets", ticketTypes);
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        // return DB name to html
        modelAndView.addObject("users", users);
        /*
        setViewName -> {name}.html, file name
         */
        modelAndView.addObject("create");
        return modelAndView;
    }


    @PostMapping("/sava_ticket")
    public ModelAndView createFeatureTicket(@ModelAttribute("tickets") TicketType ticketType) {

        ModelAndView modelAndView = new ModelAndView("redirect:/pm_dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        ticketTypeService.createTicket(ticketType, user.getFullName(), "Feature");
        modelAndView.addObject("tickets", ticketType);
        return modelAndView;
    }

    @GetMapping("/update_ticket/{id}")
    public ModelAndView updateTicket(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("update_ticket");
        TicketType ticketType = ticketTypeService.getTicketById(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        modelAndView.addObject("featureType", ticketType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/delete_ticket/{id}")
    public ModelAndView deleteTicketView(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/pm_dashboard");
        ticketTypeService.deleteTicket(id);
        return modelAndView;
    }

    @GetMapping("/to_json_feature/{id}")
    public ModelAndView toJsonTicket(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("json_feature_page");
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        TicketType ticketType = ticketTypeService.getTicketById(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        modelAndView.addObject("featureType", gsonPretty.toJson(ticketType));
        modelAndView.addObject("users", users);

        return modelAndView;
    }

}
