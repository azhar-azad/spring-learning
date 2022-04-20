/**
 * Why this class is needed?
 * 
 * There are several checkbox elements in the form, all 
 * with the name ingredients, and a text input element named name. Those fields in the  
 * form correspond directly to the ingredients and name properties of the Taco class.
 * 
 * The name field on the form needs to capture only a simple textual value. Thus the 
 * name property of Taco is of type String. The ingredients check boxes also have textual 
 * values, but because zero or many of them may be selected, the ingredients property 
 * that they’re bound to is a List<Ingredient> that will capture each of the chosen ingredients.
 * 
 * If the ingredients check boxes have textual (e.g., String ) values, but the 
 * Taco object represents a list of ingredients as List<Ingredient>, then isn’t there a 
 * mismatch? How can a textual list like ["FLTO", "GRBF", "LETC"] be bound to a list of 
 * Ingredient objects that are richer objects containing not only an ID but also a 
 * descriptive name and ingredient type?
 * 
 * That’s where a converter comes in handy. A converter is any class that implements 
 * Spring’s Converter interface and implements its convert() method to take one 
 * value and convert it to another. To convert a String to an Ingredient, we’ll use the 
 * IngredientByIdConverter as follows.
 * */

package tacos.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
	
	private IngredientRepository ingredientRepository;
	
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

//	private Map<String, Ingredient> ingredientMap = new HashMap<>();
//	
//	public IngredientByIdConverter() {
//		ingredientMap.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
//		ingredientMap.put("COTO", new Ingredient("FLTO", "Corn Tortilla", Type.WRAP));
//		ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
//		ingredientMap.put("CARN", new Ingredient("CARN", "Carnitas", Type.VEGGIES));
//		ingredientMap.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
//		ingredientMap.put("LETC", new Ingredient("LETC", "Lettuce", Type.VEGGIES));
//		ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Type.CHEESE));
//		ingredientMap.put("JACK", new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
//		ingredientMap.put("SLSA", new Ingredient("SLSA", "Salsa", Type.SAUCE));
//		ingredientMap.put("SRCR", new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
//	}

	@Override
	public Ingredient convert(String id) {
		return ingredientRepository.findById(id).orElse(null);
//		return ingredientMap.get(id);
	}

}
