package com.azad.musiclibrary.services.impl;

import com.azad.musiclibrary.models.dtos.AppUserDto;
import com.azad.musiclibrary.exceptions.ResourceNotFoundException;
import com.azad.musiclibrary.models.entities.AppUserEntity;
import com.azad.musiclibrary.repositories.AppUserRepository;
import com.azad.musiclibrary.services.AppUserService;
import com.azad.musiclibrary.utils.AppUtils;
import com.azad.musiclibrary.utils.PagingAndSortingObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    private AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUserDto create(AppUserDto requestDto) {

        AppUserEntity appUserEntity = appUserRepository.save(modelMapper.map(requestDto, AppUserEntity.class));

        return modelMapper.map(appUserEntity, AppUserDto.class);
    }

    @Override
    public List<AppUserDto> getAll(PagingAndSortingObject ps) {

        Pageable pageable;

        if (ps.getSort().isEmpty()) {
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        } else {
            Sort sortBy = appUtils.getSortBy(ps.getSort(), ps.getOrder());
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), sortBy);
        }

        List<AppUserEntity> appUserEntities = appUserRepository.findAll(pageable).getContent();

        List<AppUserDto> appUserDtos = new ArrayList<>();
        appUserEntities.forEach(appUserEntity -> appUserDtos.add(modelMapper.map(appUserEntity, AppUserDto.class)));

        return appUserDtos;
    }

    @Override
    public AppUserDto getById(Long id) {

        AppUserEntity appUserEntity = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppUserEntity", id));

        return modelMapper.map(appUserEntity, AppUserDto.class);
    }

    @Override
    public AppUserDto updateById(Long id, AppUserDto updatedRequestDto) {

        AppUserEntity appUserEntity = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppUserEntity", id));

        if (updatedRequestDto.getFirstName() != null)
            appUserEntity.setFirstName(updatedRequestDto.getFirstName());
        if (updatedRequestDto.getLastName() != null)
            appUserEntity.setLastName(updatedRequestDto.getLastName());
        if (updatedRequestDto.getEmail() != null)
            appUserEntity.setEmail(updatedRequestDto.getEmail());
        if (updatedRequestDto.getUsername() != null)
            appUserEntity.setUsername(updatedRequestDto.getUsername());
        if (updatedRequestDto.getPassword() != null)
            appUserEntity.setPassword(updatedRequestDto.getPassword());

        AppUserEntity updatedAppUserEntity = appUserRepository.save(appUserEntity);

        return modelMapper.map(updatedAppUserEntity, AppUserDto.class);
    }

    @Override
    public void deleteById(Long id) {

        AppUserEntity appUserEntity = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppUserEntity", id));

        appUserRepository.delete(appUserEntity);
    }
}
