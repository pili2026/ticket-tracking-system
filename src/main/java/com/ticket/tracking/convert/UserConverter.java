package com.ticket.tracking.convert;

import com.ticket.tracking.entity.user.User;
import com.ticket.tracking.entity.user.UserRequest;
import com.ticket.tracking.entity.user.UserResponse;

public class UserConverter {
    private UserConverter() {}

    public static UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setAccount(user.getAccount());
        response.setPassword(user.getPassword());
        response.setAuthority(user.getAuthority());
        response.setUserType(user.getUserType());
        return response;
    }

    public static User toUser(UserRequest request) {
        User user = new User();
        user.setAccount(request.getAccount());
        user.setPassword(request.getPassword());
        user.setAuthority(request.getAuthority());
        user.setUserType(request.getUserType());

        return user;
    }
}
