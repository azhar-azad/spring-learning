package com.azad.tacos.kitchen.messaging.jms.listener;

import com.azad.tacos.TacoOrder;
import com.azad.tacos.kitchen.KitchenUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/***
 * The OrderListener class listens passively for messages, rather than actively requesting them.
 */
@Profile("jms-listener")
@Component
public class JmsOrderListener {

    private KitchenUI ui;

    @Autowired
    public JmsOrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    /***
     * This method is annotated with JmsListener to "listen" for messages on the tacocloud.order.queue destination.
     * It doesn't deal with JmsTemplate, nor is explicitly invoked by our application code. Instead, framework code
     * within Spring waits for messages to arrive at the specified destination, and when they arrive, the
     * receiveOrder() method is invoked automatically with the message's TacoOrder payload as a parameter.
     * @param order
     */
    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(TacoOrder order) {
        ui.displayOrder(order);
    }
}
