package com.azad.tacocloud.tacos.data;

import com.azad.tacocloud.tacos.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
