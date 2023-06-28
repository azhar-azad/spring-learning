package com.azad.onlinecourse.models.instructor;

import com.azad.onlinecourse.models.auth.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InstructorDto extends Instructor {

    private Long id;
}
