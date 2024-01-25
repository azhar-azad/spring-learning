package com.azad.tacocloud.tacos.messaging;

import com.azad.tacocloud.tacos.TacoOrder;

public interface OrderMessagingService {

    void sendOrder(TacoOrder order);
    void convertAndSendOrder(TacoOrder order);
}
