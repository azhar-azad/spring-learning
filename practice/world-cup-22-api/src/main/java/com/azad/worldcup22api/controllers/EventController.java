package com.azad.worldcup22api.controllers;

import com.azad.worldcup22api.handlers.EventHandler;
import com.azad.worldcup22api.handlers.FactoryEventHandler;
import com.azad.worldcup22api.models.Event;
import com.azad.worldcup22api.models.dtos.EventDto;
import com.azad.worldcup22api.models.responses.EventResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/events")
public class EventController {

    @Autowired
    private ModelMapper modelMapper;

    private FactoryEventHandler eventHandlerFactory;


    @PostMapping
    public ResponseEntity<EventResponse> handleEventRequest(@RequestBody Event event) {
        EventDto eventDto = modelMapper.map(event, EventDto.class);
        EventHandler eventHandler = eventHandlerFactory.getEventHandler(eventDto.getMatchEvent().toUpperCase());
        EventDto handledEvent = eventHandler.handleEvent(eventDto);
        return new ResponseEntity<>(modelMapper.map(handledEvent, EventResponse.class), HttpStatus.OK);
    }
}
