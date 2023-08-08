package com.azad.taskapiwithauth.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AppUserDto extends AppUser {

    private Long id;
    private String roleName;
    private Role role;
}
