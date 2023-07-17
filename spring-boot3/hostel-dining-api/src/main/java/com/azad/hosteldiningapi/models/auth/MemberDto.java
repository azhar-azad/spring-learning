package com.azad.hosteldiningapi.models.auth;

import com.azad.hosteldiningapi.models.DtoModel;
import com.azad.hosteldiningapi.models.memberinfo.MemberInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto extends Member implements DtoModel {

    private Long id;
    private String roleName;

    @Override
    public Long getId() {
        return id;
    }
}
