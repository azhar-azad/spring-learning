package com.azad.prospring5.ch3.setter_injection.annotation;

import com.azad.prospring5.ch2.decoupled.MessageProvider;
import org.springframework.stereotype.Component;

// simple bean
@Component("provider")
public class HelloWorldMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "Different Hello World!";
    }
}
