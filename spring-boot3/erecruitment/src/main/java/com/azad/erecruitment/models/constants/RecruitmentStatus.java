package com.azad.erecruitment.models.constants;

import lombok.Getter;

@Getter
public enum RecruitmentStatus {

    APPLIED("Applicant Applied"),
    SHORTLISTED("CV Shortlisted"),
    ON_ASSESSMENT("Appeared on Written Test"),
    INTERVIEW("Interview Scheduled"),
    OFFERED("Company Offered The Post"),
    HIRED("Company Hired The Applicant"),
    DISQUALIFIED("Disqualified On Any Step"),
    ON_HOLD("Hold");

    private final String value;

    RecruitmentStatus(String value) {
        this.value = value;
    }
}
