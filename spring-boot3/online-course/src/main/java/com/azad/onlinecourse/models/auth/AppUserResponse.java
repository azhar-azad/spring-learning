package com.azad.onlinecourse.models.auth;

import com.azad.onlinecourse.common.exception.ApiError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AppUserResponse extends AppUser {

    private Long id;
    private Role role;
    private ApiError error;
}
