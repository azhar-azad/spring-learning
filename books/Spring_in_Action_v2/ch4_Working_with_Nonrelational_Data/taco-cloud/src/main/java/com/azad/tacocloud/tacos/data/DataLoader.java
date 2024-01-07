package com.azad.tacocloud.tacos.data;

import com.azad.tacocloud.tacos.Ingredient;
import com.azad.tacocloud.tacos.Ingredient.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader implements CommandLineRunner {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public void run(String... args) throws Exception {
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
    }
}
