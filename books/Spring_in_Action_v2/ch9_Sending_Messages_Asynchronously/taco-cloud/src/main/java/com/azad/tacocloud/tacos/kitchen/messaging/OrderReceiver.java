package com.azad.tacocloud.tacos.kitchen.messaging;

import com.azad.tacocloud.tacos.TacoOrder;
import jakarta.jms.JMSException;

public interface OrderReceiver {
    TacoOrder receiveOrder();
    TacoOrder receiveOrderAndConvert();
}
