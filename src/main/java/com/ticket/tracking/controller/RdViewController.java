package com.ticket.tracking.controller;

import com.ticket.tracking.entity.BugType;
import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.TestCaseType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.service.CustomLoginUserDetailsService;
import com.ticket.tracking.service.FeatureService;
import com.ticket.tracking.service.QaTypeService;
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
    private FeatureService featureService;

    @Autowired
    private QaTypeService qaTypeService;

    @Autowired
    private CustomLoginUserDetailsService customLoginUserDetailsService;

    @GetMapping("/rd_dashboard")
    public ModelAndView rdTickets() {
        ModelAndView modelAndView = new ModelAndView("rd_dashboard");
        List<BugType> bugTypes = qaTypeService.getBugTypeTickets();
        List<FeatureType> featureTypes = featureService.getFeatureTickets();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        modelAndView.addObject("featureTypes", featureTypes);
        modelAndView.addObject("bugTypes", bugTypes);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/savaBugTicket")
    public ModelAndView createQaTicket(@ModelAttribute("tickets") BugType bugType) {

        ModelAndView modelAndView = new ModelAndView("redirect:/rd_dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        qaTypeService.createBugTicket(bugType, user.getFullName());
        modelAndView.addObject("tickets", bugType);
        return modelAndView;
    }

    @GetMapping("/updateBugTicket/{id}")
    public ModelAndView updateBugTicket(@PathVariable("id") String id) {
        System.out.println("updateTicketView");
        ModelAndView modelAndView = new ModelAndView("update_bug_ticket");
        BugType bugType = qaTypeService.getBugTicket(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        modelAndView.addObject("bugType", bugType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }
}
