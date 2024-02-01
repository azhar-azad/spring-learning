package com.azad.tacocloud.tacos.messaging;

import com.azad.tacocloud.tacos.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService {

    private KafkaTemplate<String, TacoOrder> kafkaTemplate;

    @Autowired
    public KafkaOrderMessagingService(KafkaTemplate<String, TacoOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        kafkaTemplate.send("tacocloud.orders.topic", order);
        /*
        If the topic is set as default on the application.yml file, then
         */
        kafkaTemplate.sendDefault(order);
    }

    @Override
    public void convertAndSendOrder(TacoOrder order) {

    }
}
