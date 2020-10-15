package com.ticket.tracking.controller;

import com.ticket.tracking.entity.User;
import com.ticket.tracking.entity.UserResponse;
import com.ticket.tracking.exception.InvalidValueException;
import com.ticket.tracking.service.CustomLoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    CustomLoginUserDetailsService customLoginUserDetailsService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = customLoginUserDetailsService.findUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/add_user")
    public ResponseEntity<User> createUser(User user, BindingResult bindingResult) {
        User userExists = customLoginUserDetailsService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidValueException("Invalid user info");
        } else {
            customLoginUserDetailsService.saveUser(user);
        }

        return ResponseEntity.ok(user);

    }

}
