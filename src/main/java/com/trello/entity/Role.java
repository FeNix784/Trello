package com.trello.entity;

public enum Role {

    CREATOR,
    ADMIN,
    USER;

    public static Role returnRole(int roleIndex){
        if (roleIndex == 1) return Role.ADMIN;
        else return Role.USER;
    }
}
