package com.azad.tacocloud.tacos.messaging;

import com.azad.tacocloud.tacos.TacoOrder;
import jakarta.jms.Destination;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Primary
public class JmsOrderMessagingService implements OrderMessagingService {

    private JmsTemplate jms;
    private Destination orderQueue;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        /*
        Send order:
        The sendOrder() method calls the jms.send(), passing in an anonymous
        inner-clas implementation of MessageCreator. That implementation overrides
        createMessage() to create a new object message from the given TacoOrder object.
        This send() method doesn't specify a destination. The destination
        should be pulled from configuration (application.yml).
        In many cases, using a default destination is the easiest choice.
         */
        jms.send(session -> session.createObjectMessage(order));

        /*
        Send order with Destination object:
        If we ever need to send a message to a destination other than the default
        destination, we'll need to specify that destination as a parameter to
        send().
         */
        jms.send(orderQueue, session -> session.createObjectMessage(order));

        /*
        Send order with Destination name:
        Specifying the destination with a Destination object affords us the opportunity to configure the Destination with
        more than just the destination name. But in practice, we'll almost never specify anything more than the
        destination name. It's often easier to just send the name as the first parameter to send() as shown in the
        following method.
         */
        jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));

        /*
        Send order with post processor:
        If Taco Cloud decided to open a few taco joints in addition to its web business, we need a way to communicate
        the source of an order to the kitchens at the restaurants (whether the source is "WEB" or "STORE"). An easier
        solution would be to add a custom header to the message to carry the order's source. If we were using the send()
        method, this could easily be accomplished by calling setStringProperty() on the Message object.
         */
        jms.send("tacocloud.order.queue", session -> {
            Message message = session.createObjectMessage(order);
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }


    @Override
    public void convertAndSendOrder(TacoOrder order) {
        /*
        Convert and send order:
        The above methods aren't particularly difficult to use, a sliver of complexity is added by requiring that we
        provide a MessageCreator. It would be simpler if we needed to specify only the object that's to be sent (and
        optionally the destination). The JmsTemplate's convertAndSend() method simplifies message publication by
        eliminating the need to provide a MessageCreator. Instead, we pass the object that's to be sent directly to
        convertAndSend(), and the object will be converted into a Message before being sent.
         */
        jms.convertAndSend("tacocloud.order.queue", order);

        /*
        Convert and send order with post processor:
         * If we use convertAndSend() instead of the send() method, the Message object is created under the covers, and we
         * don't have access to it. Fortunately, we have a way to tweak a Message created under the covers before it's sent.
         * By passing in a MessagePostProcessor as the final parameter to convertAndSend(), we can do whatever we want with
         * the Message after it has been created. As the MessagePostProcessor is a functional interface, we can simplify it
         * a bit by replacing the anonymous inner class with a lambda.
         */
        jms.convertAndSend("tacocloud.order.queue", order, message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }
}
