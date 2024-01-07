package com.azad.tacocloud.tacos.web;

import com.azad.tacocloud.tacos.jdbc.Ingredient;
import com.azad.tacocloud.tacos.jdbc.Taco;
import com.azad.tacocloud.tacos.jdbc.TacoOrder;
import com.azad.tacocloud.tacos.data.jdbc.JdbcIngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.azad.tacocloud.tacos.jdbc.Ingredient.Type;

/***
 * A controller that will do the following:
 *      - Handle HTTP GET requests where the request path is /design
 *      - Build a list of ingredients
 *      - Hand off the request and the ingredient data to a view template to be rendered as HTML and send to the
 *      requesting web browser
 *
 * @Slf4j - is a Lombok-provided annotation that, at compilation time, will automatically generate an SLF4J Logger
 * static property in the class. This annotation has the same effect as if we were to explicitly add the following lines
 * within the class:
 *      private static final org.slf4j.Logger log =
 *          org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);
 * @Controller - serves to identify this class as a controller and to mark it as a candidate for component scanning,
 * so that Spring will discover it and automatically create an instance of DesignTacoController as a bean in the
 * Spring application context.
 * @RequestMapping - when applied at the class level, specifies the kind of requests that this controller handles. In
 * this case, it specifies that DesignTacoController will handle requests whose path begins with /design.
 * @SessionAttributes - indicates that the TacoOrder object that is put into the model a little later in the class
 * should be maintained in session. This is important because the creation of a taco is also the first step in creating
 * an order, and the order we create will need to be carried in the session so that it can span multiple requests.
 */
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final JdbcIngredientRepository jdbcIngredientRepository;

    @Autowired
    public DesignTacoController(JdbcIngredientRepository jdbcIngredientRepository) {
        this.jdbcIngredientRepository = jdbcIngredientRepository;
    }

    /***
     * The addIngredientsToModel() method uses the injected IngredientRepository's findAll() method to fetch all
     * ingredients from the database. It then filters them into distinct ingredient types before adding them to the
     * model.
     * @param model
     */
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = (List<Ingredient>) jdbcIngredientRepository.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    /***
     * **********
     * ch3: This method will not be used as we have introduced the database support now. The @ModelAttribute is removed.
     * See the addIngredientsToModel() method above.
     * **********
     * This method will be invoked when a request is handled and will construct a list of Ingredient objects to be put
     * into the model. The list is hardcoded for now.
     * @param model is an object that ferries data between a controller and whatever view is charged with rendering
     *              the data. Ultimately, data that's placed in Model attributes is copied into servlet request
     *              attributes, where the view can find them and use them to render a page in the user's browser.
     */
//    @ModelAttribute
    public void addIngredientsToModelHardcoded(Model model) {

        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

        /*
        As the list of ingredients is ready, the next lines will filter the list by ingredient type using a helper
        method filterByType(). A list of ingredient types is then added as an attribute to the Model object that will
        be passed into showDesignForm() method.
         */
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    /***
     * Creates a new TacoOrder object to place into the model. The TacoOrder object, referred to in the @SessionAttributes
     * annotation, holds state for the order being built as the user creates tacos across multiple requests.
     * The @ModelAttribute annotation exposes command objects to a web view, using specific attribute names.
     * @return
     */
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    /***
     * Creates a new Taco object to place into the model. The Taco object is placed into the model so that the view
     * rendered in response to the GET request for /design will have a non-null object to display.
     * @return
     */
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    /***
     * The @GetMapping, paired with the class level @RequestMapping, specifies that when an HTTP GET request is
     * received for /design, Spring MVC will call showDesignForm() method to handle the request.
     * @return a String value of "design", which is the logical name of the view that will be used to render the model
     *      to the browser. But before it does that, it also populates the given Model with an empty Taco object
     *      under a key whose name is "design". This will enable the form to have a blank slate on which to create a
     *      taco masterpiece.
     */
    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    /***
     * The @PostMapping annotation coordinates with the class-level @RequestMapping to indicate that processTaco()
     * method should handle POST requests for /design.
     * The @ModelAttribute applied to the TacoOrder parameter indicates that it should use the TacoOrder object that
     * was placed into the model via the @ModelAttribute-annotated order() method.
     * The processTaco() method finishes by returning a String value. The value returned indicates a view that will be
     * shown to the user. It is prefixed with "redirect:", indicating that this a redirect view. More specifically, it
     * indicates that after processTaco() method completes, the user's browser should be redirected to the relative
     * path /orders/current, which will be handled on the OrderController class.
     *
     * @Valid - annotation tells Spring MVC to perform validation on the submitted Taco object after it's bound to the
     * submitted form data and before the processTaco() method is called.
     * @param taco The fields in the form are bound to properties of a Taco object when the form(design) is submitted.
     * @param tacoOrder
     * @return
     */
    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {

        /*
        If there are any validation errors, the details of those errors will be captured in an Errors object that's
        passed into processTaco() method. If there are, the method concludes without processing the Taco and returns
        the 'design' view name so that the form is redisplayed.
         */
        if (errors.hasErrors()) {
            return "design";
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }

    /*
    Private methods
     */
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
