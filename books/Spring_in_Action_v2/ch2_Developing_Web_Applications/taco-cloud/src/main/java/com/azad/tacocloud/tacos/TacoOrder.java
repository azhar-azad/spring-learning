package com.azad.tacocloud.tacos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * TacoOrder class is needed to define how customers specify the tacos that they want to order, along with payment and
 * delivery information.
 */
@Data
public class TacoOrder {

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
