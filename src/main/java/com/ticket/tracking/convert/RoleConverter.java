package com.ticket.tracking.convert;

import com.ticket.tracking.entity.*;

public class RoleConverter {
    private RoleConverter() {}

    public static RoleResponse toRoleResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setAccount(role.getAccount());
        response.setPassword(role.getPassword());
        response.setAuthority(role.getAuthority());
        response.setRoleType(role.getRoleType());
        return response;
    }

    public static Role toRole(RoleRequest request) {
        Role role = new Role();
        role.setAccount(request.getAccount());
        role.setPassword(request.getPassword());
        role.setAuthority(request.getAuthority());
        role.setRoleType(request.getRoleType());

        return role;
    }
}
