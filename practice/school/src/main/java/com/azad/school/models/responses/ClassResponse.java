package com.azad.school.models.responses;

import com.azad.school.models.Class;

public class ClassResponse extends Class {

    private Long classId;

    public ClassResponse() {
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
