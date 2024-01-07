package com.azad.tacocloud.tacos.web;

import com.azad.tacocloud.tacos.Ingredient;
import com.azad.tacocloud.tacos.data.jdbc.JdbcIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/***
 * In the form element on the design.html template, we can see several checkbox elements, all with the name
 * 'ingredients', and a text input element named 'name'. Those fields in the form correspond directly to the
 * 'ingredients' and 'name' properties of the 'Taco' class.
 *
 * The 'name' field on the form needs to capture only a simple textual value. Thus, the 'name' property of 'Taco' is of
 * type String. The ingredients checkboxes also have textual values, but because zero or many of them may be selected,
 * the 'ingredients' property that they're bound to is a 'List<Ingredient>' that will capture each of the chosen
 * ingredients.
 *
 * If the ingredients checkboxes have textual (e.g., String) values, but the 'Taco' object represents a list of
 * ingredients as 'List<Ingredient>', then isn't there a mismatch? How can a textual list like ["FLTO", "GRBF", "LETC"]
 * be bound to a list of Ingredient objects that are richer objects containing not only an ID but also a descriptive
 * name and ingredient type?
 *
 * That's where a converter comes in handy. A converter is any class that implements Spring's Converter interface and
 * implements its convert() method to take one value and convert it to another.
 *
 * @Component - to make the class discoverable as a bean in the Spring application context. Spring Boot
 * autoconfiguration will discover this, and any other Converter beans, and will automatically register them with
 * Spring MVC to be used when the conversion of request parameters to bound properties is needed.
 */
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private JdbcIngredientRepository jdbcIngredientRepository;

    @Autowired
    public IngredientByIdConverter(JdbcIngredientRepository jdbcIngredientRepository) {
        this.jdbcIngredientRepository = jdbcIngredientRepository;
    }

    /***
     * The convert() method simply takes a String and uses it to look up the Ingredient from the ingredientMap.
     * @param id The id property of Ingredient
     * @return The Ingredient object with the id passed as argument
     */
    @Override
    public Ingredient convert(String id) {
//        return ingredientMap.get(id);
        return jdbcIngredientRepository.findById(id).orElse(null);
    }

    /***
     * Because we don't yet have a database from which to pull Ingredient objects, the constructor creates a Map keyed
     * on a String that is the ingredient's ID and whose values are the Ingredient objects.
     *
     * ch3: Now that we have a database to fetch the Ingredients, we can simplify this class with a simple call to the
     * IngredientRepository.findById() method. We don't need the map variable and the hardcoded constructor.
     */
    private final Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        /*
        ingredientMap.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientMap.put("COTO", new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        ingredientMap.put("CARN", new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        ingredientMap.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        ingredientMap.put("LETC", new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredientMap.put("JACK", new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        ingredientMap.put("SLSA", new Ingredient("SLSA", "Salsa", Type.SAUCE));
        ingredientMap.put("SRCR", new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
         */
    }
}
