package com.ticket.tracking.controller;

import com.ticket.tracking.entity.RoleRequest;
import com.ticket.tracking.entity.RoleResponse;
import com.ticket.tracking.entity.TicketRequest;
import com.ticket.tracking.entity.TicketResponse;
import com.ticket.tracking.parameter.RoleQueryParameter;
import com.ticket.tracking.parameter.TicketQueryParameter;
import com.ticket.tracking.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoles(@ModelAttribute RoleQueryParameter param) {
        List<RoleResponse> roles = roleService.getRoleResponses(param);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{role_type}")
    public ResponseEntity<List<RoleResponse>> getRolesByType(@PathVariable("role_type") String roleType) {
        List<RoleResponse> roles = roleService.getRoleResponsesByType(roleType);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{role_type}/{id}")
    public ResponseEntity<RoleResponse> getRolesByTypeId(@PathVariable("role_type") String roleType,
                                                         @PathVariable("id") String id) {

        RoleResponse role = roleService.getRoleResponsesByTypeId(roleType, id);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
        RoleResponse role = roleService.createRole(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();

        return ResponseEntity.created(location).body(role);
    }
}
