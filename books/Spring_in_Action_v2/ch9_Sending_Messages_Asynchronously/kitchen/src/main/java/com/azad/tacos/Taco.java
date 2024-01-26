package com.azad.tacos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * Taco is a straightforward Java domain class with a couple of properties.
 *
 * @NotNull - annotation is used when we want to ensure that a property isn't empty or null.
 * @Size - annotation is used to specify some attributes regarding length.
 * @ManyToMany - To declare the relationship between a Taco and its associated Ingredient, we annotate ingredients
 * with @ManyToMany. A Taco can have many ingredient objects, and an Ingredient can be a part of many Tacos.
 * @RestResource - annotation lets us give the entity any relation name and path we want. In this case, we're setting
 * them both to "tacos".
 */
@Entity
@Data
@RestResource(rel = "tacos", path = "tacos")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
