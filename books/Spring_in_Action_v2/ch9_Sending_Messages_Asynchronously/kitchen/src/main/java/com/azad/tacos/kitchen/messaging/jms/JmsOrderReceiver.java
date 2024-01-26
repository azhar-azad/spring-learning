package com.azad.tacos.kitchen.messaging.jms;

import com.azad.tacos.TacoOrder;
import com.azad.tacos.kitchen.messaging.OrderReceiver;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class JmsOrderReceiver implements OrderReceiver {

    private JmsTemplate jms;
    private MessageConverter converter;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
        this.converter = converter;
    }

    /***
     * Here we've used a String to specify the destination from which to pull an order. The receive() method returns an
     * unconverted Message. But what we really need is the TacoOrder that's inside the Message, so the very next thing
     * that happens is that we use an injected message converter to convert the message.
     * @return
     */
    @Override
    public TacoOrder receiveOrder() {
        Message message = jms.receive("tacocloud.order.queue");
        try {
            return message != null ? (TacoOrder) converter.fromMessage(message) : null;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Receiving a raw Message object might be useful in some cases where we need to inspect the message's properties
     * and headers. But often we need only the payload. Converting that payload to a domain type is a two-step process
     * and requires that the message converter be injected into the component. When we care only about the message's
     * payload, receiveAndConvert() is a lot simpler. We no longer need to inject a MessageConverter, because all the
     * message conversion will be done behind the scenes.
     * @return
     */
    @Override
    public TacoOrder receiveOrderAndConvert() {
        return (TacoOrder) jms.receiveAndConvert("tacocloud.order.queue");
    }
}
