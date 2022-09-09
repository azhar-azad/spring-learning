package com.azad.ecommerce.services.impl;

import com.azad.ecommerce.models.dtos.UserProfileDto;
import com.azad.ecommerce.models.entities.UserEntity;
import com.azad.ecommerce.models.entities.UserProfileEntity;
import com.azad.ecommerce.repos.UserProfileRepository;
import com.azad.ecommerce.security.auth.AuthService;
import com.azad.ecommerce.services.UserProfileService;
import com.azad.ecommerce.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    private final UserProfileRepository repository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserProfileDto create(UserProfileDto request) {

        UserEntity loggedInUser = authService.getLoggedInUser();

        UserProfileEntity userProfileEntity = modelMapper.map(request, UserProfileEntity.class);
        userProfileEntity.setUser(loggedInUser);

        UserProfileEntity userProfile = repository.save(userProfileEntity);

        return modelMapper.map(userProfile, UserProfileDto.class);
    }

    @Override
    public List<UserProfileDto> getAll(Map<String, String> reqParams) {

        return null;
    }

    @Override
    public UserProfileDto getById(Long id) {
        return null;
    }

    @Override
    public UserProfileDto updateById(Long id, UserProfileDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
