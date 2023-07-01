package com.azad.onlinecourse.common.generics;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class PojoModel {

    private String uid;

    public abstract String getUid();
}
