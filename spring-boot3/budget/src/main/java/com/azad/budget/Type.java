package com.azad.budget;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

    NEED("N"),
    WANT("W"),
    SAVING("S");

    private final String shortName;
}
