package com.azad.tacos.kitchen.messaging;


import com.azad.tacos.TacoOrder;

public interface OrderReceiver {
    TacoOrder receiveOrder();
    TacoOrder receiveOrderAndConvert();
}
