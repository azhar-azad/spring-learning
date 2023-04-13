package com.azad.authenticationservice.models.dtos;

import com.azad.authenticationservice.models.pojos.AppUser;
import com.azad.authenticationservice.models.pojos.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
public class AppUserDto extends AppUser {

    private String id;
    private Role role;
}
