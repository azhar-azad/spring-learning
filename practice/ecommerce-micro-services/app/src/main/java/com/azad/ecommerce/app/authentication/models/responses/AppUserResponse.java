package com.azad.ecommerce.app.authentication.models.responses;

import com.azad.ecommerce.app.authentication.models.pojos.AppUser;
import com.azad.ecommerce.app.authentication.models.pojos.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class AppUserResponse extends AppUser {

    private String id;
    private String userUid;
    private Role role;
}
