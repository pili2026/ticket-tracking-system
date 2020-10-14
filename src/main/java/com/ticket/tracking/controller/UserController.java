package com.ticket.tracking.controller;

import com.ticket.tracking.entity.TicketResponse;
import com.ticket.tracking.entity.UserResponse;
import com.ticket.tracking.repository.UserRepository;
import com.ticket.tracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> userResponses = userService.findUsers();
        return ResponseEntity.ok(userResponses);
    }

}
