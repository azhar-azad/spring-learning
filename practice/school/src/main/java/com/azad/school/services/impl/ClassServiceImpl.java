package com.azad.school.services.impl;

import com.azad.school.models.dtos.ClassDto;
import com.azad.school.repos.ClassRepo;
import com.azad.school.services.ClassService;
import com.azad.school.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepo classRepo;

    @Autowired
    public ClassServiceImpl(ClassRepo classRepo) {
        this.classRepo = classRepo;
    }

    @Override
    public ClassDto create(ClassDto requestDto) {
        return null;
    }

    @Override
    public List<ClassDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public ClassDto getById(Long id) {
        return null;
    }

    @Override
    public ClassDto updateById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
