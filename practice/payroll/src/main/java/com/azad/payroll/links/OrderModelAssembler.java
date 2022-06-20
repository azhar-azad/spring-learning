package com.azad.payroll.links;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order order) {
        // Unconditional links to single-item resource and aggregate root
        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderRestController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderRestController.class).all()).withRel("orders"));

        // Conditional links based on state of the order
        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderRestController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderRestController.class).complete(order.getId())).withRel("complete"));
        }

        return orderModel;
    }
}
