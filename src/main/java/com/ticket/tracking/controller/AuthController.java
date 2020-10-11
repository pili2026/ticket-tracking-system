package com.ticket.tracking.controller;

import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.service.CustomLoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    private CustomLoginUserDetailsService loginUserService;

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
        LoginUser userExists = loginUserService.findUserByEmail(user.getEmail());
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
        loginUserService.saveUser(user);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("user", new LoginUser());
        return modelAndView;
    }

    @GetMapping("/update_user/{id}")
    public ModelAndView updateUser(@PathVariable("id") String id, LoginUser user) {
        ModelAndView modelAndView = new ModelAndView("update_user");
        LoginUser userExists = loginUserService.findUserById(id);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("user", userExists);
        return modelAndView;
    }

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        List<LoginUser> users = loginUserService.findUsers();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }


    @GetMapping(value = {"/", "/home"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/deleteUser/{id}")
    public ModelAndView deleteTicketView(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
        loginUserService.deleteUser(id);
        return modelAndView;
    }
}
