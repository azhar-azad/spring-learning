package com.azad.tacocloud.tacos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

/***
 * Taco is a straightforward Java domain class with a couple of properties.
 *
 * @NotNull - annotation is used when we want to ensure that a property isn't empty or null.
 * @Size - annotation is used to specify some attributes regarding length.
 */
@Data
public class Taco {

    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;
}
