package com.azad.tacocloud.tacos.security;

import com.azad.tacocloud.tacos.data.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * Although Spring Security handles many aspects of security, it really isn't directly involved in the process of user
 * registration, so we're going to rely on a little bit of Spring MVC to handle that task.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /***
     * A GET request for /register will be handled by the registerForm() method.
     * @return A logical view name of registration.
     */
    @GetMapping
    public String registerForm() {
        return "registration";
    }

    /***
     * When the registrationForm is submitted, the processRegistration() method will handle the HTTPS POST request.
     * The form fields will be bound to a RegistrationForm object by Spring MVC and passed into the processRegistration()
     * method for processing.
     * @param form
     * @return
     */
    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
