package com.azad.CampusConnectApi.services.impl;

import com.azad.CampusConnectApi.exceptions.ResourceNotFoundException;
import com.azad.CampusConnectApi.models.dtos.AppUserDto;
import com.azad.CampusConnectApi.models.entities.AppUserEntity;
import com.azad.CampusConnectApi.repositories.AppUserRepository;
import com.azad.CampusConnectApi.services.AppUserService;
import com.azad.CampusConnectApi.utils.AppUtils;
import com.azad.CampusConnectApi.utils.PagingAndSortingObject;
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

        if (appUtils.getAge(requestDto.getDateOfBirth()) < 18) {
            throw new RuntimeException("User with less than 18 years old are not allowed");
        }

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

        List<AppUserEntity> appUsers = appUserRepository.findAll(pageable).getContent();

        List<AppUserDto> dtos = new ArrayList<>();
        appUsers.forEach(appUser -> dtos.add(modelMapper.map(appUser, AppUserDto.class)));

        return dtos;
    }

    @Override
    public AppUserDto getById(Long id) {

        AppUserEntity appUser = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppUserEntity", id));

        if (appUser == null) return null;

        return modelMapper.map(appUser, AppUserDto.class);
    }

    @Override
    public AppUserDto updateById(Long id, AppUserDto updatedRequestDto) {

        AppUserEntity appUser = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppUserEntity", id));

        if (appUser == null) return null;

        if (updatedRequestDto.getUsername() != null) {
            appUser.setUsername(updatedRequestDto.getUsername());
        }
        if (updatedRequestDto.getEmail() != null) {
            appUser.setEmail(updatedRequestDto.getEmail());
        }
        if (updatedRequestDto.getFirstName() != null) {
            appUser.setFirstName(updatedRequestDto.getFirstName());
        }
        if (updatedRequestDto.getLastName() != null) {
            appUser.setLastName(updatedRequestDto.getLastName());
        }

        AppUserEntity updatedAppUser = appUserRepository.save(appUser);

        return modelMapper.map(updatedAppUser, AppUserDto.class);
    }

    @Override
    public void deleteById(Long id) {

        AppUserEntity appUser = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppUserEntity", id));

        appUserRepository.delete(appUser);
    }
}
