package com.azad.tacocloud.tacos;

import lombok.Data;

/***
 * When we save a TacoOrder, we also must save the Taco objects that go with it. And when we save the Taco objects,
 * we'll also need to save an object that represents the link between the Taco and each Ingredient that makes up the
 * Taco. The IngredientRef class defines that linking between Taco and Ingredient.
 */
@Data
public class IngredientRef {

    private final String ingredient;
}
