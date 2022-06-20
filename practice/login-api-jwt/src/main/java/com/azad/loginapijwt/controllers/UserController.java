package com.azad.loginapijwt.controllers;

import com.azad.loginapijwt.entity.User;
import com.azad.loginapijwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/info")
    public User getUserInfo() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userRepo.findByEmail(email).isPresent())
            return userRepo.findByEmail(email).get();

        return null;
    }
}
