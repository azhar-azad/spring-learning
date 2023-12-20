package com.azad.multiplex.model.pojo;

import com.azad.multiplex.model.constant.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Role {

    @NotBlank
    @Enumerated(EnumType.STRING)
    private RoleType roleName;
}
