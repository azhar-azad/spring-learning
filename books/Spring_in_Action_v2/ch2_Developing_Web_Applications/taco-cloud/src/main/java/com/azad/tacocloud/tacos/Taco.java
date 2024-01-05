package com.azad.tacocloud.tacos;

import lombok.Data;

import java.util.List;

/***
 * Taco is a straightforward Java domain class with a couple of properties.
 */
@Data
public class Taco {

    private String name;
    private List<Ingredient> ingredients;
}
