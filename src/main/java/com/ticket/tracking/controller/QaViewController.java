package com.ticket.tracking.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ticket.tracking.entity.BugType;
import com.ticket.tracking.entity.TestCaseType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.service.QaTypeService;
import com.ticket.tracking.service.CustomLoginUserDetailsService;
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
    private QaTypeService qaTypeService;

    @Autowired
    private CustomLoginUserDetailsService customLoginUserDetailsService;

    @GetMapping("/qa_dashboard")
    public ModelAndView qaTickets() {
        ModelAndView modelAndView = new ModelAndView("qa_dashboard");
        List<BugType> bugTypes = qaTypeService.getBugTypeTickets();
        List<TestCaseType> testCaseTypes = qaTypeService.getTestCaseTypeTickets();
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

    @PostMapping("/savaQaTypeTicket")
    public ModelAndView createQaTicket(@ModelAttribute("tickets") BugType bugType) {

        ModelAndView modelAndView = new ModelAndView("redirect:/qa_dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        qaTypeService.createBugTicket(bugType, user.getFullName());
        modelAndView.addObject("tickets", bugType);
        return modelAndView;
    }


    @GetMapping("/updateQaTypeTicket/{id}")
    public ModelAndView updateQaTicket(@PathVariable("id") String id) {
        System.out.println("updateTicketView");
        ModelAndView modelAndView = new ModelAndView("update_qa_ticket");
        BugType bugType = qaTypeService.getBugTicket(id);
        TestCaseType testCaseType = qaTypeService.getTestCaseTicket(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        modelAndView.addObject("testCaseType", testCaseType);
        modelAndView.addObject("bugType", bugType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/deleteQaTicket/{id}")
    public ModelAndView deleteTicketView(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/qa_dashboard");
        qaTypeService.deleteTicket(id);
        return modelAndView;
    }

    @GetMapping("/to_json_ticket/{id}")
    public ModelAndView toJsonTicket(@PathVariable("id") String id, LoginUser user) {
        ModelAndView modelAndView = new ModelAndView("json_ticket_page");
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        BugType bugType = qaTypeService.getBugTicket(id);
        TestCaseType testCaseType = qaTypeService.getTestCaseTicket(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();

        modelAndView.addObject("testCaseType", gsonPretty.toJson(testCaseType));
        modelAndView.addObject("bugType", gsonPretty.toJson(bugType));
        modelAndView.addObject("users", users);

        return modelAndView;
    }

}
