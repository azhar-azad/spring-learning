package com.azad.onlinecourse.service;

import com.azad.onlinecourse.common.ApiUtils;
import com.azad.onlinecourse.common.PagingAndSorting;
import com.azad.onlinecourse.models.auth.AppUser;
import com.azad.onlinecourse.models.auth.AppUserEntity;
import com.azad.onlinecourse.models.instructor.InstructorDto;
import com.azad.onlinecourse.models.instructor.InstructorEntity;
import com.azad.onlinecourse.repository.AppUserRepository;
import com.azad.onlinecourse.repository.InstructorRepository;
import com.azad.onlinecourse.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final InstructorRepository repository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository repository) {
        this.repository = repository;
    }

    @Override
    public InstructorDto create(InstructorDto dto) {

        InstructorEntity entity = modelMapper.map(dto, InstructorEntity.class);

        InstructorEntity savedEntity = repository.save(entity);

        AppUserEntity loggedInUser = authService.getLoggedInUser();
        loggedInUser.setInstructor(savedEntity);
        loggedInUser.setRole(roleRepository.findByRoleName("INSTRUCTOR").orElse(null));
        appUserRepository.save(loggedInUser);

        savedEntity.setUser(loggedInUser);

        InstructorDto savedDto = modelMapper.map(savedEntity, InstructorDto.class);
        savedDto.setFirstName(loggedInUser.getFirstName());
        savedDto.setLastName(loggedInUser.getLastName());
        savedDto.setEmail(loggedInUser.getEmail());
        savedDto.setProfilePicUrl(loggedInUser.getProfilePicUrl());
        savedDto.setCreatedAt(loggedInUser.getCreatedAt());
        savedDto.setModifiedAt(loggedInUser.getModifiedAt());

        return savedDto;
    }

    @Override
    public List<InstructorDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public InstructorDto getById(Long id) {
        return null;
    }

    @Override
    public InstructorDto updateById(Long id, InstructorDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
