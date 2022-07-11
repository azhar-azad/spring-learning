package com.azad.onlineed.controllers;

import com.azad.onlineed.models.Student;
import com.azad.onlineed.models.dtos.StudentDto;
import com.azad.onlineed.models.responses.ApiResponse;
import com.azad.onlineed.models.responses.StudentResponse;
import com.azad.onlineed.services.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(path = "/api/students")
public class StudentController {

    @Autowired
    private ModelMapper modelMapper;

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
}
