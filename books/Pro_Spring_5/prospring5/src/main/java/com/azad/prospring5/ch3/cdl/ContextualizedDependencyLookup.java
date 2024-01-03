package com.azad.prospring5.ch3.cdl;

public class ContextualizedDependencyLookup implements ManagedComponent {

    private Dependency dependency;

    @Override
    public void performLookup(Container container) {
        this.dependency = (Dependency) container.getDependency("myDependency");
    }

    @Override
    public String toString() {
        return dependency.toString();
    }
}
