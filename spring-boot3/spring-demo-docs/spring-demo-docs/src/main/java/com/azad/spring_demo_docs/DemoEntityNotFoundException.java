package com.azad.spring_demo_docs;

public class DemoEntityNotFoundException extends RuntimeException {

    public DemoEntityNotFoundException(Long id) {
        super("Could not find entity with id " + id);
    }
}
