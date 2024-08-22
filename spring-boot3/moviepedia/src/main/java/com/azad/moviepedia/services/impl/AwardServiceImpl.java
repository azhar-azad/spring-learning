package com.azad.moviepedia.services.impl;

import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.dtos.AwardDto;
import com.azad.moviepedia.services.AwardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwardServiceImpl implements AwardService {

    @Override
    public AwardDto create(AwardDto awardDto) throws RuntimeException {
        return null;
    }

    @Override
    public List<AwardDto> getAll(PagingAndSorting ps) throws RuntimeException {
        return List.of();
    }

    @Override
    public AwardDto getById(Long id) throws RuntimeException {
        return null;
    }

    @Override
    public AwardDto updateById(Long id, AwardDto updatedDto) throws RuntimeException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws RuntimeException {

    }
}
