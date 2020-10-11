package com.ticket.tracking.controller;

import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.service.CustomLoginUserDetailsService;
import com.ticket.tracking.service.FeatureService;
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
    private FeatureService featureService;

    @Autowired
    private CustomLoginUserDetailsService customLoginUserDetailsService;


    @GetMapping("/pm_dashboard")
    public ModelAndView featureTickets() {
        ModelAndView modelAndView = new ModelAndView("pm_dashboard");
        System.out.println("dashboard page");
        List<FeatureType> featureTypes = featureService.getFeatureTickets();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        modelAndView.addObject("tickets", featureTypes);
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


    @PostMapping("/savaTicket")
    public ModelAndView createFeatureTicket(@ModelAttribute("tickets") FeatureType featureType) {

        ModelAndView modelAndView = new ModelAndView("redirect:/pm_dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        featureService.createFeatureTicket(featureType, user.getFullName());
        modelAndView.addObject("tickets", featureType);
        return modelAndView;
    }

    @GetMapping("/updateTicket/{id}")
    public ModelAndView updateTicket(@PathVariable("id") String id) {
        System.out.println("updateTicketView");
        ModelAndView modelAndView = new ModelAndView("update_ticket");
        FeatureType featureType = featureService.getFeatureTicket(id);
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        modelAndView.addObject("featureType", featureType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }
//
//    @PostMapping("/updateTicket")
//    public ModelAndView updateFeatureTicket(@ModelAttribute("tickets") FeatureType featureType) {
//
//        ModelAndView modelAndView = new ModelAndView("redirect:/pm_dashboard");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
//        featureService.replaceTicketTypeById(featureType, user.getFullName());
//        modelAndView.addObject("tickets", featureType);
//        return modelAndView;
//    }

    @GetMapping("/deleteTicket/{id}")
    public ModelAndView deleteTicketView(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/pm_dashboard");
        featureService.deleteTicket(id);
        return modelAndView;
    }

}
