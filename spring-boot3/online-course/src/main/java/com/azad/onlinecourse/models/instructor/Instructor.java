package com.azad.onlinecourse.models.instructor;

import com.azad.onlinecourse.models.auth.AppUser;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Instructor extends AppUser {

    @NotNull(message = "Instructor's headline cannot be empty")
    private String headline;

    @NotNull(message = "Instructor's biography cannot be empty")
    private String biography;

    private String website;
}
