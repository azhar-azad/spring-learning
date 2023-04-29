package com.azad.authenticationservice.models.requests;

import com.azad.authenticationservice.models.pojos.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RegistrationRequest extends AppUser {

    private String roleName;
}
