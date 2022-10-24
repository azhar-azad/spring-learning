package com.azad.springdockerjenkins.controller;

import com.azad.springdockerjenkins.entity.ThemeParkRide;
import com.azad.springdockerjenkins.repository.ThemeParkRideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class ThemeParkRideController {

    private final ThemeParkRideRepository repository;

    @Autowired
    public ThemeParkRideController(ThemeParkRideRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/rides", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ThemeParkRide> getRides() {
        return repository.findAll();
    }

    @GetMapping(value = "/rides/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ThemeParkRide getRide(@PathVariable long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Invalid ride id %s", id)));
    }

    @PostMapping(value = "/rides", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ThemeParkRide createRide(@Valid @RequestBody ThemeParkRide themeParkRide) {
        return repository.save(themeParkRide);
    }
}
