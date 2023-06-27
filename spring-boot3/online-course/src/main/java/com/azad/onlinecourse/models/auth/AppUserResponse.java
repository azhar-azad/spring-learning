package com.azad.onlinecourse.models.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AppUserResponse extends AppUser {

    private Long id;
    private Role role;
}
