package com.azad.school.models;

public enum Role {

    ROLE_ADMIN,
    ROLE_USER,
    ROLE_STUDENT,
    ROLE_TEACHER;

    public static boolean isValidRole(String value) {

        return value.equalsIgnoreCase(ROLE_ADMIN.name())
                || value.equalsIgnoreCase(ROLE_USER.name())
                || value.equalsIgnoreCase(ROLE_STUDENT.name())
                || value.equalsIgnoreCase(ROLE_TEACHER.name());
    }
}
