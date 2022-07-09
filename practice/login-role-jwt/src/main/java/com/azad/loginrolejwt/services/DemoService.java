package com.azad.loginrolejwt.services;

import com.azad.loginrolejwt.entities.Demo;

import java.util.List;

public interface DemoService {

    Demo create(Demo request);
    List<Demo> getAll();
    Demo getById(Long id);
    Demo updateById(Long id, Demo request);
    void deleteById(Long id);
}
