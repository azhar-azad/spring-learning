package com.azad.hrsecured.models;

public enum Role {

    ROLE_ADMIN,
    ROLE_USER;

    public static boolean isValidRole(String value) {
        if (value.equalsIgnoreCase(ROLE_USER.name()))
            return true;
        else
            return value.equalsIgnoreCase(ROLE_ADMIN.name());
    }
}
