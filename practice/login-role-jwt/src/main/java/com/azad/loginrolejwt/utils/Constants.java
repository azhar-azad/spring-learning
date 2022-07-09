package com.azad.loginrolejwt.utils;

public class Constants {

    /**
     * ROLE_BASED_AUTH:
     *      Roles are the authentication specification by which
     *      this application will recognize an application user.
     *      Ex: USER, ADMIN, MANAGER, etc..
     *
     *      By theory, a role is comprised with one or more authorities.
     *      For example, the USER role can have only the authority to view,
     *      whereas the ADMIN role can have the authorities to view, create and edit.
     *
     *      If true, then the application will provide access to resources to a logged-in user
     *      by its role, not by its authorities.
     *          # Registration request will send an array of roles.
     *          # AuthService will check the roles against the roles saved in the database.
     *            Those roles have to be saved beforehand on the "roles" table.
     *          # Only Users with ADMIN role can create new role.
     * */
    public static Boolean ROLE_BASED_AUTH = true;

    /**
     * AUTHORITY_BASED_AUTH:
     *      Authorities are the authentication specification by which
     *      this application can recognize what a user can do to the resources.
     *      Ex: CREATOR, EDITOR, VIEWER, etc..
     *
     *      By theory, one or more authorities can be under a specific role.
     * */
    public static Boolean AUTHORITY_BASED_AUTH = false;

    public static String JWT_SECRET = "x3n0m1at23g4v4n";
}
