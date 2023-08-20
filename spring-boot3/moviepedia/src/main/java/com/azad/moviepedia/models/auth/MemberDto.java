package com.azad.moviepedia.models.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto extends Member {

    private Long id;
    private String roleName;
}