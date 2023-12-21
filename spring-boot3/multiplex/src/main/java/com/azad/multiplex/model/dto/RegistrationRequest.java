package com.azad.multiplex.model.dto;

import com.azad.multiplex.model.constant.RoleType;
import com.azad.multiplex.model.pojo.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequest extends Member {

    private RoleType roleName;
}
