package com.azad.hosteldiningapi.models.auth;

import com.azad.hosteldiningapi.common.generics.DtoModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto extends Member implements DtoModel {

    private Long id;
    private String roleName;
}
