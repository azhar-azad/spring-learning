package com.azad.tacocloud.tacos.web;

import com.azad.tacocloud.tacos.TacoOrder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    /***
     * The orderForm() method will handle HTTP GET requests for /orders/current.
     * @return The logical name of the view template orderForm.
     */
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    /***
     * We need to add a method on this controller that will handle POST requests for /orders.
     * @param order When the method is called to handle a submitted order, it's given a TacoOrder object whose
     *              properties are bound to the submitted form fields.
     * @param sessionStatus The TacoOrder object was initially created and placed into the session when the user
     *                      created their first taco. By calling setComplete() on the sessionStatus, we are
     *                      ensuring that the session is cleaned up and ready for a new order the next time the user
     *                      creates a taco.
     * @return This method will return the process to the root path. 
     */
    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
