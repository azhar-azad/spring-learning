package com.azad.tacocloud.tacos.data.dataJDBC;

import com.azad.tacocloud.tacos.jdbc.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
