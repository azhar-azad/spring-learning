package com.azad.supershop.model.constant;

import lombok.Getter;

@Getter
public enum ProductUnit {

    KG("kg"),
    G("gm"),
    L("litre"),
    ML("ml"),
    DOZ("dozen"),
    P("piece");

    private final String value;

    ProductUnit(String value) {
        this.value = value;
    }
}
