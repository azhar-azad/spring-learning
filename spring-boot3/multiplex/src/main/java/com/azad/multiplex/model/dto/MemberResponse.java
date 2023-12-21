package com.azad.multiplex.model.dto;

import com.azad.multiplex.model.pojo.Member;
import com.azad.multiplex.model.pojo.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberResponse extends Member {

    private Long id;
    private Role role;
}
