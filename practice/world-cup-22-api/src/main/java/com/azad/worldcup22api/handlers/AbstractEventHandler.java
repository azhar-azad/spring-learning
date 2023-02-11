package com.azad.worldcup22api.handlers;

import com.azad.worldcup22api.models.dtos.EventDto;
import com.azad.worldcup22api.repos.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEventHandler implements EventHandler {

    @Autowired
    protected MatchRepository matchRepository;
    @Autowired
    protected TeamRepository teamRepository;
    @Autowired
    protected TeamStatRepository teamStatRepository;
    @Autowired
    protected PlayerRepository playerRepository;
    @Autowired
    protected PlayerStatRepository playerStatRepository;

    @Override
    public abstract EventDto handleEvent(EventDto eventDto);
}
