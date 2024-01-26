package com.azad.tacos.kitchen;

import com.azad.tacos.TacoOrder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KitchenApplication {

	public static void main(String[] args) {
		SpringApplication.run(KitchenApplication.class, args);
	}

	@Bean
	public MappingJackson2MessageConverter messageConverter() {
		MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        /*
        We called setTypeIdPropertyName() on the MappingJackson2MessageConverter before returning it. This is very
        important, because it enables the receiver to know what type to convert an incoming message to. By default,
        it will contain the fully qualified classname of the type being converted. But this method is somewhat
        inflexible, requiring that the receiver also have the same type, with the same fully qualified classname.
         */
		messageConverter.setTypeIdPropertyName("_typeId");

        /*
        To allow more flexibility, we can map a synthetic type name to the actual type by calling setTypeIdMappings()
        on the message converter. For example, the following change to the message converter bean method maps a
        synthetic TacoOrder type ID to the TacoOrder class.
        Instead of the fully qualified classname being sent in the message's _typeId property, the value "TacoOrder"
        will be sent.
         */
		Map<String, Class<?>> typeIdMappings = new HashMap<>();
		typeIdMappings.put("order", TacoOrder.class);
		messageConverter.setTypeIdMappings(typeIdMappings);

		return messageConverter;
	}
}
