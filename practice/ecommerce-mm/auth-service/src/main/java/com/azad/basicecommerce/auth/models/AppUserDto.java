package com.azad.basicecommerce.auth.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AppUserDto extends AppUser {

    private Long id;
    private Role role;
    private String roleName;
}
