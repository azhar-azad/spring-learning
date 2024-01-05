package com.azad.tacocloud.tacos;

import lombok.Data;

/***
 * This is a simple Java domain class, defining the three properties needed to describe an ingredient.
 */
@Data
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
