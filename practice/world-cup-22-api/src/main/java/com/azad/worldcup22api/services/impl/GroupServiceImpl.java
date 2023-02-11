package com.azad.worldcup22api.services.impl;

import com.azad.worldcup22api.models.dtos.MatchDto;
import com.azad.worldcup22api.repos.GroupRepository;
import com.azad.worldcup22api.services.GroupService;
import com.azad.worldcup22api.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    @Autowired
    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public MatchDto create(MatchDto reqBody) {
        return null;
    }

    @Override
    public MatchDto getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public MatchDto getById(Long id) {
        return null;
    }

    @Override
    public MatchDto updateById(Long id, MatchDto updatedReqBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
