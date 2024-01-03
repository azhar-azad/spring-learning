package com.azad.prospring5.ch2.annotated;

import com.azad.prospring5.ch2.decoupled.HelloWorldMessageProvider;
import com.azad.prospring5.ch2.decoupled.MessageProvider;
import com.azad.prospring5.ch2.decoupled.MessageRenderer;
import com.azad.prospring5.ch2.decoupled.StandardOutMessageRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {

    // equivalent to <bean id="provider" class=".."/>
    @Bean
    public MessageProvider provider() {
        return new HelloWorldMessageProvider();
    }

    // equivalent to <bean id="renderer" class=".."/>
    @Bean
    public MessageRenderer renderer() {
        MessageRenderer renderer = new StandardOutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }
}
