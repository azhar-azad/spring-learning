package com.azad.school.models.dtos;

import com.azad.school.models.Student;

public class StudentDto extends Student {

    private Long id;

    public StudentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
