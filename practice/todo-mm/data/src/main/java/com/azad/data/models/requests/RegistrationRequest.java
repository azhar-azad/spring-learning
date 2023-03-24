package com.azad.data.models.requests;

import com.azad.data.models.pojos.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequest extends AppUser {

    private String roleName;
}
