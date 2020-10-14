package com.ticket.tracking.convert;

import com.ticket.tracking.entity.Ticket;
import com.ticket.tracking.entity.TicketResponse;
import com.ticket.tracking.entity.User;
import com.ticket.tracking.entity.UserResponse;

public class UserConverter {

    public static UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setFullName(user.getFullName());
        response.setEnabled(user.isEnabled());
        response.setRoles(user.getRoles());
        response.setUserRole(user.getUserRole());

        return response;
    }

}
