package com.azad.data.models.responses;

import com.azad.data.models.pojos.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserResponse extends AppUser {

    private Long id;
}
