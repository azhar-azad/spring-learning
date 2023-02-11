package com.azad.worldcup22api.handlers;

import com.azad.worldcup22api.models.dtos.EventDto;

public interface EventHandler {

    EventDto handleEvent(EventDto eventDto);
}
