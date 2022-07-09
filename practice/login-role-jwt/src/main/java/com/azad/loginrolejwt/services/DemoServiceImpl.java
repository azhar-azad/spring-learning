package com.azad.loginrolejwt.services;

import com.azad.loginrolejwt.entities.Demo;
import com.azad.loginrolejwt.repos.DemoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService{

    private final DemoRepo demoRepo;

    @Autowired
    public DemoServiceImpl(DemoRepo demoRepo) {
        this.demoRepo = demoRepo;
    }

    @Override
    public Demo create(Demo request) {
        return null;
    }

    @Override
    public List<Demo> getAll() {
        return null;
    }

    @Override
    public Demo getById(Long id) {
        return null;
    }

    @Override
    public Demo updateById(Long id, Demo request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
