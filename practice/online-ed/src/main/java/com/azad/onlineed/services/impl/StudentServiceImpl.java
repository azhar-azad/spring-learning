package com.azad.onlineed.services.impl;

import com.azad.onlineed.models.dtos.StudentDto;
import com.azad.onlineed.repos.StudentRepo;
import com.azad.onlineed.services.StudentService;
import com.azad.onlineed.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private ModelMapper modelMapper;

    private final StudentRepo studentRepo;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public StudentDto create(StudentDto studentDto) {
        return null;
    }

    @Override
    public List<StudentDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public StudentDto getById(Long id) {
        return null;
    }

    @Override
    public StudentDto updateById(Long id, StudentDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
