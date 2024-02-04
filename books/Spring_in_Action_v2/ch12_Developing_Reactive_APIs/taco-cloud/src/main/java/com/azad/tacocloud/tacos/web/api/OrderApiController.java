package com.azad.tacocloud.tacos.web.api;

import com.azad.tacocloud.tacos.TacoOrder;
import com.azad.tacocloud.tacos.data.OrderRepository;
import com.azad.tacocloud.tacos.messaging.OrderMessagingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {

    private OrderRepository repo;
    private OrderMessagingService messagingService;

    public OrderApiController(OrderRepository repo, OrderMessagingService messagingService) {
        this.repo = repo;
        this.messagingService = messagingService;
    }


    @PostMapping(consumes = "applciation/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        /*
        We are calling sendOrder() when an order is created.
         */
        messagingService.sendOrder(order);
        return repo.save(order);
    }
}
