package com.ticket.tracking.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class RoleRequest {

    @NotEmpty(message = "Account is undefined.")
    private String account;

    @NotEmpty(message = "Password is undefined.")
    private String password;

    @NotEmpty(message = "RoleType is undefined.")
    private String roleType;

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

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

}
