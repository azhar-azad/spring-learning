package com.azad.tacocloud.tacos.web.api;

import com.azad.tacocloud.tacos.Taco;
import com.azad.tacocloud.tacos.TacoOrder;
import com.azad.tacocloud.tacos.data.OrderRepository;
import com.azad.tacocloud.tacos.data.TacoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/***
 * @RestController - annotation serves two purposes. First, it's a stereotype annotation like @Controller and @Service
 * that marks a class for discovery by component scanning. It tells Spring that all handler methods in the controller
 * should have their return value written directly to the body of the response, rather than being carried in the model
 * to a view for rendering. Alternatively, we could have annotated TacoController with @Controller. But then we'd need
 * to also annotate all the handler methods with @ResponseBody to achieve the same result.
 * @RequestMapping - annotation at the class level works with the @GetMapping to specify that the recentTacos() method
 * is responsible for handling GET requests for /api/tacos?recent. The produces attribute specifies that any of the handler
 * methods in TacoController will handle requests only if the client sends a request with an "Accept" header that
 * includes "application/json", indicating that the client can handle responses only in JSON format.
 * @CrossOrigin - It's common for JavaScript-based user interface, such as those written in a framework like Angular or
 * ReactJS, to be served from a separate host and/or port from the API, and the web browser will prevent our client from
 * consuming the API. This restriction can be overcome by including CORS headers in the server responses. Spring makes
 * it easy to apply CORS with the @CrossOrigin annotation. It allows clients from localhost, port 8080, to access the
 * API. The origins attribute accepts an array, however, so we can also specify multiple values,
 * like @CrossOrigin(origins={"http://tacocloud:8080", "http://tacocloud.com"}).
 */
//@RestController
//@RequestMapping(path = "/api/tacos", produces = "application/json")
//@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {

    private TacoRepository tacoRepo;
    private OrderRepository orderRepo;

    public TacoController(TacoRepository tacoRepo, OrderRepository orderRepo) {
        this.tacoRepo = tacoRepo;
        this.orderRepo = orderRepo;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        /*
        A PageRequest object that specifies that we want only the first (0th) page of 12 results, sorted in descending
        order by the taco's creation date.
         */
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }

    /***
     * Handles GET requests for /api/tacos/{id}, where the {id} portion of the path is a placeholder. The actual value
     * in the request is given to the id parameter, which is mapped to the {id} placeholder by @PathVariable.
     * Spring will take the Optional<Taco> and calls its get() method to produce the response. If the ID doesn't match
     * any known tacos, the response body will contain "null" and the response's HTTP status code will be 200 (OK). The
     * client is handed a response it can't use, but the status code indicates everything is fine. A better approach
     * would be to return a response with an HTTP 404 (NOT FOUND) status. 
     * @param id The id of the Taco to fetch
     * @return An Optional<Taco> because it is possible that there may not be a taco that matches the given ID.
     */
//    @GetMapping("/{id}")
    public Optional<Taco> tacoById(@PathVariable("id") Long id) {
        return tacoRepo.findById(id);
    }

    /***
     * As it's currently written there's no easy wat to return a 404 status code from tacoById() method. To do this,
     * instead of returning a Taco object, tacoById2() returns a ResponseEntity<Taco>.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById2(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /***
     * Ultimately taco data will come from users when they craft their taco creations. Therefore, we'll need to handle
     * requests containing taco designs and save them to the database. This method will handle an HTTP POST request, it's
     * annotated with @PostMapping instead of @GetMapping. We use the consume attribute to say that the method will only
     * handle requests whose Content-type matches application/json.
     * The method's Taco parameter is annotated with @RequestBody to indicate that the body of the request should be
     * converted to a Taco object and bound to the parameter.
     * The method also annotated with @ResponseStatus(HttpStatus.CREATED). Under normal circumstances (when no exceptions
     * are thrown), all responses will have an HTTP status code of 200 (OK), indicating that the request was successful.
     * In the case of POST request, an HTTP status of 201 (CREATED) is more descriptive. 
     * @param taco
     * @return
     */
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder putOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrder order) {
        order.setId(orderId);
        return orderRepo.save(order);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrder patch) {
        TacoOrder order = orderRepo.findById(orderId).get();
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryZip());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        return orderRepo.save(order);
    }
    /*
    Spring MVC's mapping annotations, including @PatchMapping and @PutMapping, specify only what kinds of requests a
    method should handle. These annotations don't dictate how the request will be handled. Even though PATCH semantically
    implies a partial update, it's up to us to write code in the handler method that actually performs such an update.
     */

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            /*
            We are doing nothing after catching the exception. My thinking here is that if we try to delete a resource
            that doesn't exist, the outcome is the same as if it did exist prior to deletion -- that is, the resource
            will be non-existent. Whether it existed before is irrelevant. Alternatively, I could've written the
            method to return a ResponseEntity, setting the body to null and the HTTP status code to NOT FOUND.
             */
        }
    }
}

