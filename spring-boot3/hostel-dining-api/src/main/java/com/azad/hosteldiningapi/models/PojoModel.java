package com.azad.hosteldiningapi.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class PojoModel {

    protected String uid;

    public abstract String getUid();
}
