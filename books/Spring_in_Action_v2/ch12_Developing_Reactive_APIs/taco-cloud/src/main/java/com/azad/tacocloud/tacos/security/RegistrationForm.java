package com.azad.tacocloud.tacos.security;

import com.azad.tacocloud.tacos.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * RegistrationForm is a basic Lombok class with a handful of properties.
 */
@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    /***
     * The toUser() method uses the properties to create a new User object, which is what processRegistration() will
     * save, using the injected UserRepository.
     * @param passwordEncoder
     * @return
     */
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), fullName, street, city, state, zip, phone);
    }
}
