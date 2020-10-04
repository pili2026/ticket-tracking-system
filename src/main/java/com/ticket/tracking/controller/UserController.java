package com.ticket.tracking.controller;

import com.ticket.tracking.entity.user.UserRequest;
import com.ticket.tracking.entity.user.UserResponse;
import com.ticket.tracking.parameter.UserQueryParameter;
import com.ticket.tracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(@ModelAttribute UserQueryParameter param) {
        List<UserResponse> roles = userService.getUserResponses(param);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{user_type}")
    public ResponseEntity<List<UserResponse>> getUsersByType(@PathVariable("user_type") String userType) {
        List<UserResponse> roles = userService.getUserResponsesByType(userType);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{user_type}/{id}")
    public ResponseEntity<UserResponse> getUserByTypeId(@PathVariable("user_type") String userType,
                                                         @PathVariable("id") String id) {

        UserResponse role = userService.getUserResponsesByTypeId(userType, id);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse role = userService.createUser(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();

        return ResponseEntity.created(location).body(role);
    }

    @PutMapping("/{user_type}/{account}")
    public ResponseEntity<UserResponse> replaceUser(
            @PathVariable("user_type") String userType, @PathVariable("account") String account,
            @Valid @RequestBody UserRequest request) {
        UserResponse role = userService.replaceUserTypeByAccount(userType, account, request);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
