package com.azad.todolist.models.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Priority {

    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private final String value;
}
