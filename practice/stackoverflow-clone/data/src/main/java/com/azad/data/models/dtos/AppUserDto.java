package com.azad.data.models.dtos;

import com.azad.data.models.pojos.AppUser;
import com.azad.data.models.pojos.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDto extends AppUser {

    private Long id;
    private Role role;
    private String roleName;
}
