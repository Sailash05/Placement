package com.placementserver.placementserver.responses;

public class RoleAndName {
    private String role;
    private long userName;

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public long getUserName() {
        return userName;
    }
    public void setUserName(long userName) {
        this.userName = userName;
    }

    public RoleAndName() {
        super();
    }
    public RoleAndName(String role, long userName) {
        this.userName = userName;
        this.role = role;
    }
}
