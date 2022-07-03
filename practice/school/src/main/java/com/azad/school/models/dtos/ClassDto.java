package com.azad.school.models.dtos;

import com.azad.school.models.Class;

public class ClassDto extends Class {

    private Long classId;

    public ClassDto() {
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
