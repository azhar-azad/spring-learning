package com.azad.todolist.models.dtos;

import com.azad.todolist.models.pojos.Member;
import com.azad.todolist.models.pojos.Role;
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
