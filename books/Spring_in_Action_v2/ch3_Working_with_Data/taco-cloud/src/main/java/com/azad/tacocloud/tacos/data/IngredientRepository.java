package com.azad.tacocloud.tacos.data;

import com.azad.tacocloud.tacos.Ingredient;

import java.util.Optional;

public interface IngredientRepository {

    /***
     * Query for all ingredients into a collection of Ingredient objects.
     * @return An iterable collection of Ingredients
     */
    Iterable<Ingredient> findAll();

    /***
     * Query for a single ingredient by its id
     * @param id The ingredient id to fetch
     * @return An Optional of the fetched Ingredient
     */
    Optional<Ingredient> findById(String id);

    /***
     * Save an Ingredient object
     * @param ingredient The Ingredient object to save
     * @return The saved Ingredient
     */
    Ingredient save(Ingredient ingredient);
}
