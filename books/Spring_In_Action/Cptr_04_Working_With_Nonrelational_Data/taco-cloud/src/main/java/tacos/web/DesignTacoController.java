/**
 * This controller class will do the following:
 * 		- Handle HTTP GET requests where the request path is /design
 * 		- Build a list of ingredients
 * 		- Hand off the request and the ingredient data to a view template to be rendered 
 * 		  as HTML and sent to the requesting web browser
 * 
 * @Slf4j
 * Lombok-provided annotation that, at compilation time, 
 * will automatically generate an SLF4J Logger static property in the class. Same as 
 * private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);
 * 
 * @Controller
 * This annotation serves to identify this class as a controller and to mark it as a candidate for 
 * component scanning, so that Spring will discover it and automatically create an 
 * instance of DesignTacoController as a bean in the Spring application context.
 * 
 * @RequestMapping
 * When applied at the class level, specifies the kind of requests that this controller handles.
 * 
 * @SessionAttributes
 * This indicates that the TacoOrder object that is put into the model a 
 * little later in the class should be maintained in session. This is important because the  
 * creation of a taco is also the first step in creating an order, and the order we create will 
 * need to be carried in the session so that it can span multiple requests.
 * 
 * @GetMapping
 * @GetMapping, paired with the class-level @RequestMapping, 
 * specifies that when an HTTP GET request is received for /design, 
 * Spring MVC will call showDesignForm() to handle the request.
 * 
 * @ModelAttribute
 * Binds a method parameter or method return value to a named model attribute, and then exposes it to a web view.
 * addIngredientsToModel() method will be invoked when a request is handled and 
 * will construct a list of Ingredient objects to be put into the model.
 * A list of ingredient types is then added as an attribute to the Model object that 
 * will be passed into showDesignForm().
 * 
 * If the browser is pointed at the /design path, the DesignTacoController’s showDesignForm() 
 * and addIngredientsToModel() would be engaged, 
 * placing ingredients and an empty Taco into the model before passing the request on to the view.
 * 
 * redirect:
 * The value returned from processTaco() is prefixed with "redirect:", indicating that this is a redirect view. 
 * More specifically, it indicates that after processTaco() completes, the user’s browser 
 * should be redirected to the relative path /orders/current.
 * 
 * @Valid
 * The @Valid annotation tells Spring MVC to perform validation on the submitted Taco 
 * object after it’s bound to the submitted form data and before the processTaco() 
 * method is called.
 * 
 * Errors
 * If there are any validation errors, the details of those errors will be 
 * captured in an Errors object that’s passed into processTaco().
 * */

package tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.data.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepository;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@ModelAttribute
	public void addIngredientsToMode(Model model) {

//		List<Ingredient> ingredients = Arrays.asList(
//				new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//				new Ingredient("COTO", "Corn Tortilla", Type.WRAP), 
//				new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//				new Ingredient("CARN", "Carnitas", Type.VEGGIES),
//				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES), 
//				new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//				new Ingredient("CHED", "Cheddar", Type.CHEESE), 
//				new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//				new Ingredient("SLSA", "Salsa", Type.SAUCE), 
//				new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
		
		Iterable<Ingredient> ingredients = ingredientRepository.findAll();
		List<Ingredient> ingredientList = new ArrayList<>();
		ingredients.forEach(item -> ingredientList.add(item));

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredientList, type));
		}
	}

	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder(); // model.addAttribute("tacoOrder", new TacoOrder())
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco(); // model.addAttribute("taco", new Taco())
	}

	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	
	@PostMapping
	public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
		if (errors.hasErrors()) {
			return "design";
		}
		
		tacoOrder.addTaco(taco);
		log.info("Processing taco: {}", taco);
		
		return "redirect:/orders/current"; // not returning a view, instead returning a new route, should be caught by another route handler
	}

	private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}

}
