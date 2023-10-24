package com.azad.todolist.models.auth;

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
