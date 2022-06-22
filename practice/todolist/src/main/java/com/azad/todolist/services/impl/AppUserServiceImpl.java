package com.azad.todolist.services.impl;

import com.azad.todolist.exceptions.ResourceNotFoundException;
import com.azad.todolist.models.Roles;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.entities.AppUserEntity;
import com.azad.todolist.repos.AppUserRepo;
import com.azad.todolist.services.AppUserService;
import com.azad.todolist.utils.AppUtils;
import com.azad.todolist.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private AppUserRepo appUserRepo;

    @Autowired
    public AppUserServiceImpl(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public AppUserDto create(AppUserDto requestDto) {

        String encodedPass = passwordEncoder.encode(requestDto.getPassword());

        requestDto.setPassword(encodedPass);

        if (requestDto.getRole() == null || !Roles.isValidRole(requestDto.getRole())) {
            requestDto.setRole("ROLE_USER");
        }

        requestDto.setUserId(appUtils.getUserId(requestDto.getEmail()));

        AppUserEntity appUserEntity = appUserRepo.save(modelMapper.map(requestDto, AppUserEntity.class));

        return modelMapper.map(appUserEntity, AppUserDto.class);
    }

    @Override
    public AppUserDto getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public AppUserDto getByEntityId(String entityId) {
        return null;
    }

    @Override
    public AppUserDto updateByEntityId(String entityId, AppUserDto updatedDto) {
        return null;
    }

    @Override
    public void deleteByEntityId(String entityId) {

    }

    @Override
    public AppUserDto getByEmail(String email) {

        AppUserEntity appUserEntity = appUserRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", "email"));

        return modelMapper.map(appUserEntity, AppUserDto.class);
    }
}
