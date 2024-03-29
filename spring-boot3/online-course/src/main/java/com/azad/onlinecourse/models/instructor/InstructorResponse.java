package com.azad.onlinecourse.models.instructor;

import com.azad.onlinecourse.common.exception.ApiError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InstructorResponse extends Instructor {

    private Long id;
    private ApiError error;
}
