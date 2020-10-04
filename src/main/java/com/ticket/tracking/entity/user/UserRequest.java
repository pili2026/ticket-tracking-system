package com.ticket.tracking.entity.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class UserRequest {

    @NotEmpty(message = "Account is undefined.")
    private String account;

    @NotEmpty(message = "Password is undefined.")
    private String password;

    @NotEmpty(message = "UserType is undefined.")
    private String userType;

    @Min(value = 0, message = "Authority is invalid.")
    private int authority;


    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

}
