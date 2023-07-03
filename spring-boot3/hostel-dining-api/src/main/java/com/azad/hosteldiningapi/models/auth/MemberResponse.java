package com.azad.hosteldiningapi.models.auth;

import com.azad.hosteldiningapi.common.exceptions.ApiError;
import com.azad.hosteldiningapi.models.ResponseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberResponse extends Member implements ResponseModel {

    private Long id;
    private Role role;
    private ApiError error;
}
