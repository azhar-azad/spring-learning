package com.azad.tacos.kitchen.messaging.rabbit.listener;

import com.azad.tacos.TacoOrder;
import com.azad.tacos.kitchen.KitchenUI;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderListener {

    private KitchenUI ui;

    @Autowired
    public RabbitOrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    @RabbitListener(queues = "tacocloud.order")
    public void receiveOrder(TacoOrder order) {
        ui.displayOrder(order);
    }
}
