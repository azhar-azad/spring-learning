package com.azad.todolist.models;

public enum Roles {

    ROLE_USER,
    ROLE_ADMIN;

    public static boolean isValidRole(String value) {
        if (value.equalsIgnoreCase(ROLE_USER.name()))
            return true;
        else return value.equalsIgnoreCase(ROLE_ADMIN.name());
    }
}
