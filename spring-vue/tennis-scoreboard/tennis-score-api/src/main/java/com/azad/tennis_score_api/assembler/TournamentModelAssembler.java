package com.azad.tennis_score_api.assembler;

import com.azad.tennis_score_api.common.ApiResponseModelAssembler;
import com.azad.tennis_score_api.model.dto.TournamentDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TournamentModelAssembler implements ApiResponseModelAssembler<TournamentDto> {



    @Override
    public EntityModel<TournamentDto> toModel(TournamentDto tournamentDto) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<TournamentDto>> toCollectionModel(Iterable<? extends TournamentDto> tournamentDtos) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<TournamentDto>> getCollectionModel(Iterable<? extends TournamentDto> tournamentDtos, Map<String, String> params) {
        return null;
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<TournamentDto>> collectionModel, Map<String, String> params) {

    }

    @Override
    public void addNextLink(CollectionModel<EntityModel<TournamentDto>> collectionModel, Map<String, String> params) {

    }
}
