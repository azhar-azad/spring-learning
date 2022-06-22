package com.azad.todolist.controllers;

import com.azad.todolist.models.entities.AppUserEntity;
import com.azad.todolist.repos.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class AppUserController {

    @Autowired
    private AppUserRepo appUserRepo;

    @GetMapping("/info")
    public AppUserEntity getUserInfo() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (appUserRepo.findByEmail(email).isPresent())
            return appUserRepo.findByEmail(email).get();

        return null;
    }
}
