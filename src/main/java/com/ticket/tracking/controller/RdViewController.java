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
public class RdViewController {

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private CustomLoginUserDetailsService customLoginUserDetailsService;

    @GetMapping("/rd_dashboard")
    public ModelAndView rdTickets() {
        ModelAndView modelAndView = new ModelAndView("rd_dashboard");
        List<TicketType> bugTypes = ticketTypeService.getTicketsByType("Bug");
        List<TicketType> featureTypes = ticketTypeService.getTicketsByType("Feature");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        modelAndView.addObject("featureTypes", featureTypes);
        modelAndView.addObject("bugTypes", bugTypes);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/sava_bug_ticket")
    public ModelAndView saveBugTicket(@ModelAttribute("tickets") TicketType ticketType) {

        ModelAndView modelAndView = new ModelAndView("redirect:/rd_dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        ticketTypeService.updateTicket(ticketType);
        modelAndView.addObject("tickets", ticketType);
        return modelAndView;
    }

    @PostMapping("/sava_feature_ticket")
    public ModelAndView saveFeatureTicket(@ModelAttribute("tickets") TicketType ticketType) {

        ModelAndView modelAndView = new ModelAndView("redirect:/rd_dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        ticketTypeService.updateTicket(ticketType);
        modelAndView.addObject("tickets", ticketType);
        return modelAndView;
    }

    @GetMapping("/update_bug_ticket/{id}")
    public ModelAndView updateBugTicket(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("update_bug_ticket");
        TicketType ticketType = ticketTypeService.getTicketById(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        modelAndView.addObject("bugType", ticketType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/update_feature_ticket/{id}")
    public ModelAndView updateTicket(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("update_feature_ticket");
        TicketType ticketType = ticketTypeService.getTicketById(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        modelAndView.addObject("featureType", ticketType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("to_json_rd/{id}")
    public ModelAndView toJsonRd(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("json_rd_page");
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        TicketType ticketType = ticketTypeService.getTicketById(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();

        modelAndView.addObject("ticketType", gsonPretty.toJson(ticketType));
        modelAndView.addObject("users", users);

        return modelAndView;
    }

}
