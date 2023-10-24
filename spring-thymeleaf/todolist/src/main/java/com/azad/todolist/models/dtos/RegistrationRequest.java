package com.azad.todolist.models.dtos;

import com.azad.todolist.models.pojos.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequest extends Member {

    private String roleName;
}
