package com.azad.tacos.kitchen.messaging.rabbit;

import com.azad.tacos.TacoOrder;
import com.azad.tacos.kitchen.messaging.OrderReceiver;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderReceiver implements OrderReceiver {

    private RabbitTemplate rabbit;
    private MessageConverter converter;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter = rabbit.getMessageConverter();
    }

    @Override
    public TacoOrder receiveOrder() {
        /*
        Makes a call to the receive() method on the injected RabbitTemplate to pull an order from the tacocloud.order
        queue. It provides no time-out value, so we can assume only that the call returns immediately with either a
        Message or null. If Message is returned, we use the MessageConverter from the RabbitTemplate to convert the
        Message to a TacoOrder. Otherwise, we return a null.
        If we decide to wait up to 30 seconds before giving up, then the receive() method can be changed to
            rabbit.receive("tacocloud.order", 30000);
        We can set the timeout value on the property like following:
            spring.rabbitmq.template.receive-timeout=30
         */
        Message message = rabbit.receive("tacocloud.order");
        return message != null ? (TacoOrder) converter.fromMessage(message) : null;
    }

    @Override
    public TacoOrder receiveOrderAndConvert() {
        /*
        Using receiveAndConvert(), we can skip using the MessageConverter
         */
        TacoOrder receivedOrder = (TacoOrder) rabbit.receiveAndConvert("tacocloud.order");
        /*
        There's an alternative to casting, though. Instead, we can pass a ParameterizedTypeReference to
        receiveAndConvert() to receive a TacoOrder object directly like below. It's debatable whether that's better
        than casting, but it is a more type-safe approach than casting.
         */
        receivedOrder = rabbit.receiveAndConvert("tacocloud.order", new ParameterizedTypeReference<TacoOrder>() {});
        return receivedOrder;
    }
}
