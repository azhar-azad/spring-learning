package com.azad.prospring5.ch3.constructor_injection;

import com.azad.prospring5.ch3.cdl.Dependency;

public class ConstructorInjection {

    private Dependency dependency;

    public ConstructorInjection(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public String toString() {
        return dependency.toString();
    }
}
