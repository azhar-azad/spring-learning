package com.azad.worldcup22api.handlers;

import com.azad.worldcup22api.constants.MatchEvent;
import com.azad.worldcup22api.handlers.impl.*;

public class FactoryEventHandler {

    public EventHandler getEventHandler(String event) {
        if (MatchEvent.MATCH_SCHEDULED.name().equalsIgnoreCase(event)) {
            return new MatchScheduleEventHandler();
        } else if (MatchEvent.SQUAD_DECLARED.name().equalsIgnoreCase(event)) {
            return new SquadDeclarationEventHandler();
        } else if (MatchEvent.SUBSTITUTION.name().equalsIgnoreCase(event)) {
            return new SubstitutionEventHandler();
        } else if (MatchEvent.MATCH_STARTED.name().equalsIgnoreCase(event)) {
            return new MatchStartEventHandler();
        } else if (MatchEvent.MATCH_END.name().equalsIgnoreCase(event)) {
            return new MatchEndEventHandler();
        }
        return null;
    }
}
