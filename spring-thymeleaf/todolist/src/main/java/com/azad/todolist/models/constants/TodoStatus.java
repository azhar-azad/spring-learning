package com.azad.todolist.models.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TodoStatus {

    CREATED("Created"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
    WONT_DO("Won't Do");

    private final String value;
}
