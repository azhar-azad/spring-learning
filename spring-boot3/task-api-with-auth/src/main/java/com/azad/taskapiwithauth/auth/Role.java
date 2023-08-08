package com.azad.taskapiwithauth.auth;

import com.azad.taskapiwithauth.constants.RoleName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Role {

    @NotNull(message = "Role name cannot be empty")
    @Enumerated(EnumType.STRING)
    protected RoleName roleName;
}
