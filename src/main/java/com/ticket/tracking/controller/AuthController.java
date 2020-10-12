package com.ticket.tracking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ticket.tracking.entity.TicketType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.service.CustomLoginUserDetailsService;
import com.ticket.tracking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private CustomLoginUserDetailsService customLoginUserDetailsService;

    @Autowired
    private TicketTypeService ticketTypeService;

    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/add_user")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView();
        LoginUser user = new LoginUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("add_user");
        return modelAndView;
    }

    @PostMapping(value = "/add_user")
    public ModelAndView createNewUser(LoginUser user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
        LoginUser userExists = customLoginUserDetailsService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("add_user");
//        } else {
//            loginUserService.saveUser(user);
//            modelAndView.addObject("successMessage", "User has been registered successfully");
//            modelAndView.addObject("user", new LoginUser());
//
//        }
        customLoginUserDetailsService.saveUser(user);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("user", new LoginUser());
        return modelAndView;
    }

    @GetMapping("/update_user/{id}")
    public ModelAndView updateUser(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("update_user");
        LoginUser userExists = customLoginUserDetailsService.findUserById(id);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("user", userExists);
        return modelAndView;
    }

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        List<LoginUser> users = customLoginUserDetailsService.findUsers();
        List<TicketType> bugTypes = ticketTypeService.getTicketsByType("Bug");
        List<TicketType> testCaseTypes = ticketTypeService.getTicketsByType("TestCase");
        List<TicketType> featureTypes = ticketTypeService.getTicketsByType("Feature");
        modelAndView.addObject("users", users);
        modelAndView.addObject("testCaseTypes", testCaseTypes);
        modelAndView.addObject("bugTypes", bugTypes);
        modelAndView.addObject("tickets", featureTypes);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @GetMapping(value = {"/", "/home"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/deleteUser/{id}")
    public ModelAndView deleteTicketView(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
        customLoginUserDetailsService.deleteUser(id);
        return modelAndView;
    }

    @GetMapping("/to_json_user/{id}")
    public ModelAndView toJsonUser(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("json_user_page");
        LoginUser userExists = customLoginUserDetailsService.findUserById(id);
        String jsonFormat = jsonTest(userExists);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("json", jsonFormat);
        return modelAndView;
    }

    /* ticket */
    @GetMapping("/create_all_type_ticket")
    public ModelAndView createAllTypeTicket() {
        ModelAndView modelAndView = new ModelAndView("create_all");
        List<LoginUser> users = customLoginUserDetailsService.findUsers();
        // return DB name to html
        modelAndView.addObject("users", users);
        /*
        setViewName -> {name}.html, file name
         */

        return modelAndView;
    }

    @PostMapping("/save_all_type_ticket")
    public ModelAndView saveAllTypeTicket(@ModelAttribute("tickets") TicketType ticketType) {

        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = customLoginUserDetailsService.findUserByEmail(auth.getName());
        ticketTypeService.createTicket(ticketType, user.getFullName(), ticketType.getTicketType());
        modelAndView.addObject("tickets", ticketType);
        return modelAndView;
    }

    @GetMapping("/update_all_type_ticket/{id}")
    public ModelAndView updateAllTypeTicket(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("update_all_type_ticket");
        TicketType ticketType = ticketTypeService.getTicketById(id);
        List<LoginUser> users = customLoginUserDetailsService.findUsers();
        modelAndView.addObject("tickets", ticketType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/delete_all_type_ticket/{id}")
    public ModelAndView deleteAllTypeTicket(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
        ticketTypeService.deleteTicket(id);
        return modelAndView;
    }

    @GetMapping("/json_all_type_ticket/{id}")
    public ModelAndView jsonAllTypeTicket(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("json_page");
        List<LoginUser> users = customLoginUserDetailsService.findRDUsers();
        TicketType ticketType = ticketTypeService.getTicketById(id);
        modelAndView.addObject("tickets", ticketType);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    private String jsonTest(LoginUser loginUser) {
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        return gsonPretty.toJson(loginUser);

    }
}
