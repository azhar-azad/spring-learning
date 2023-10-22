package com.azad.erecruitment.models.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JobOpeningStatus {

    DRAFT("Drafted"),
    UNLISTED("Unlisted"),
    PUBLISH_READY("Publish Ready"),
    PUBLISHED("Published");

    private final String value;
}
