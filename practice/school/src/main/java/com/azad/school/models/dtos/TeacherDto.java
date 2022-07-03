package com.azad.school.models.dtos;

import com.azad.school.models.Teacher;

public class TeacherDto extends Teacher {

    private Long id;

    public TeacherDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
