package com.azad.wcstatapi.services.impl;

import com.azad.wcstatapi.models.dtos.GroupDto;
import com.azad.wcstatapi.models.entities.GroupEntity;
import com.azad.wcstatapi.repos.GroupRepository;
import com.azad.wcstatapi.services.GroupGenericService;
import com.azad.wcstatapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupGenericServiceImpl implements GroupGenericService {

    @Autowired
    private ModelMapper modelMapper;

    private final GroupRepository repository;

    @Autowired
    public GroupGenericServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public GroupDto create(GroupDto request) {

        GroupEntity entity = modelMapper.map(request, GroupEntity.class);

        GroupEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, GroupDto.class);
    }

    @Override
    public List<GroupDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public GroupDto getById(Long id) {
        return null;
    }

    @Override
    public GroupDto updateById(Long id, GroupDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
