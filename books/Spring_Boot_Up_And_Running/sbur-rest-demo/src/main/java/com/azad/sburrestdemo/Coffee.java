package com.azad.sburrestdemo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Coffee {

    /*
    The id field is declared final so that it can be assigned only once and never modified; as such, this also
    requires that it is assigned when creating an instance of this class and implies that it has no mutator
    method.
     */
    @Id
    private String id;
    /*
    The name field is not declared final and is thus mutable. This is a debatable design decision, but it
    serves our purpose for right now. 
     */
    private String name;

    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }

}
