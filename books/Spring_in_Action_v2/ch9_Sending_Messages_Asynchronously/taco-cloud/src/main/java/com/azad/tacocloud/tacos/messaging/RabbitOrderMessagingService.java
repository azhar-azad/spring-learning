package com.azad.tacocloud.tacos.messaging;

import com.azad.tacocloud.tacos.TacoOrder;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService {

    private RabbitTemplate rabbit;

    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        /*
        Send order with send():
        Before we can call send(), we'll need to convert a TacoOrder object to Message. That could be a tedious job,
        if not for the fact that RabbitTemplate makes its message converter readily available with a
        getMessageConverter() method.
        Once we have a MessageConverter in hand, it's simple work to convert a TacoOrder to a Message. We must supply
        any message properties with a MessageProperties, but if we don't need to set any such properties, a default
        instance of MessageProperties is fine. Then, all that's left is to call send(), passing in the exchange and
        routing key (both of which are optional) along with the message. In this example, we're specifying only the
        routing key along with the message so the default exchange will be used.
         */
        MessageConverter converter = rabbit.getMessageConverter();
        MessageProperties props = new MessageProperties();
        Message message = converter.toMessage(order, props);
        rabbit.send("tacocloud.order", message);
        /*
        When creating our own Message objects, we can set the header through the MessageProperties instance we give to
        the message converter. We only need one additional line of code to set the header.
         */
        props.setHeader("X_ORDER_SOURCE", "WEB");
        message = converter.toMessage(order, props);
        rabbit.send("tacocloud.order", message);
    }

    @Override
    public void convertAndSendOrder(TacoOrder order) {
        /*
        Creating a Message object from the message converter is easy enough, but it's even easier to use convertAndSend()
        to let RabbitTemplate handle all of the conversion work for us.
         */
        rabbit.convertAndSend("tacocloud.order", order);
        /*
        When using convertAndSend(), we don't have quick access to the MessageProperties object. A MessagePostProcessor
        can help us with that.
         */
        rabbit.convertAndSend("tacocloud.order", order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties props = message.getMessageProperties();
                props.setHeader("X_ORDER_SOURCE", "WEB");
                return message;
            }
        });
    }
}
