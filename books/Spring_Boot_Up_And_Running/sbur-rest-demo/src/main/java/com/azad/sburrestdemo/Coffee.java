package com.azad.sburrestdemo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Coffee {

    /*
    The id field is declared final so that it can be assigned only once and never modified; as such, this also
    requires that it is assigned when creating an instance of this class and implies that it has no mutator
    method.
     */
    private final String id;
    /*
    The name field is not declared final and is thus mutable. This is a debatable design decision, but it
    serves our purpose for right now. 
     */
    @Setter
    private String name;

    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }

}
