package com.azad.ecommerce.app.authentication.models.requests;

import com.azad.ecommerce.app.authentication.models.pojos.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RegistrationRequest extends AppUser {

    private String roleName;
}
