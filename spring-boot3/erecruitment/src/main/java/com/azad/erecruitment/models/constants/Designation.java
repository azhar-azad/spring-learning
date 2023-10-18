package com.azad.erecruitment.models.constants;

import lombok.Getter;

@Getter
public enum Designation {

    JSE("Jr Software Engineer"),
    AsSE("Assistant Software Engineer"),
    AcSE("Associate Software Engineer"),
    SE("Software Engineer"),
    SSE("Senior Software Engineer"),
    TL("Team Lead"),
    PM("Project Manager"),
    DoE("Director of Engineering"),
    CTO("Chief Technology Officer"),
    CEO("Chief Executive Officer"),
    HRM("Human Resources Manager");

    private final String value;

    Designation(String value) {
        this.value = value;
    }
}
