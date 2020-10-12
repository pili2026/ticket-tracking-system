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
public class QaViewController {

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private CustomLoginUserDetailsService customLoginUserDetailsService;

    @GetMapping("/qa_dashboard")
    public ModelAndView qaTickets() {
        ModelAndView modelAndView = new ModelAndView("qa_dashboard");
        List<TicketType> bugTypes = ticketTypeService.getTicketsByType("Bug");
        List<TicketType> testCaseTypes = ticketTypeService.getTicketsByType("TestCase");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        modelAndView.addObject("testCaseTypes", testCaseTypes);
        modelAndView.addObject("bugTypes", bugTypes);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/qa_create")
    public ModelAndView qaDashboard() {

        ModelAndView modelAndView = new ModelAndView("qa_create");
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        // return DB name to html
        modelAndView.addObject("users", users);
        /*
        setViewName -> {name}.html, file name
         */
        return modelAndView;
    }

    @PostMapping("/sava_qa_type_ticket")
    public ModelAndView createQaTicket(@ModelAttribute("tickets") TicketType ticketType) {

        ModelAndView modelAndView = new ModelAndView("redirect:/qa_dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        ticketTypeService.createTicket(ticketType, user.getFullName(), ticketType.getTicketType());
        modelAndView.addObject("tickets", ticketType);
        return modelAndView;
    }


    @GetMapping("/update_qa_type_ticket/{id}")
    public ModelAndView updateQaTicket(@PathVariable("id") String id) {
        System.out.println("updateTicketView");
        ModelAndView modelAndView = new ModelAndView("update_qa_ticket");
        TicketType ticketType = ticketTypeService.getTicketById(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        modelAndView.addObject("ticketType", ticketType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/delete_qa_ticket/{id}")
    public ModelAndView deleteTicketView(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/qa_dashboard");
        ticketTypeService.deleteTicket(id);
        return modelAndView;
    }

    @GetMapping("/to_json_ticket/{id}")
    public ModelAndView toJsonTicket(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("json_ticket_page");
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        TicketType ticketType = ticketTypeService.getTicketById(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();

        modelAndView.addObject("ticket", gsonPretty.toJson(ticketType));
        modelAndView.addObject("users", users);

        return modelAndView;
    }

}
