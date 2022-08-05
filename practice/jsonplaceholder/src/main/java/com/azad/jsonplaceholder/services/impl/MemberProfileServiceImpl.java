package com.azad.jsonplaceholder.services.impl;

import com.azad.jsonplaceholder.models.dtos.MemberProfileDto;
import com.azad.jsonplaceholder.models.entities.MemberProfileEntity;
import com.azad.jsonplaceholder.repos.MemberProfileRepository;
import com.azad.jsonplaceholder.security.auth.api.AuthService;
import com.azad.jsonplaceholder.security.entities.MemberEntity;
import com.azad.jsonplaceholder.services.MemberProfileService;
import com.azad.jsonplaceholder.utils.AppUtils;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberProfileServiceImpl implements MemberProfileService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    private final MemberProfileRepository memberProfileRepository;

    @Autowired
    public MemberProfileServiceImpl(MemberProfileRepository memberProfileRepository) {
        this.memberProfileRepository = memberProfileRepository;
    }

    @Override
    public MemberProfileDto create(MemberProfileDto requestBody) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        MemberProfileEntity memberProfileEntity = modelMapper.map(requestBody, MemberProfileEntity.class);
        memberProfileEntity.setMemberEntity(loggedInMember);

        MemberProfileEntity savedMemberProfile = memberProfileRepository.save(memberProfileEntity);

        return modelMapper.map(savedMemberProfile, MemberProfileDto.class);
    }

    @Override
    public List<MemberProfileDto> getAll(PagingAndSorting ps) {

        if (!loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<MemberProfileEntity> allMemberProfilesFromDb = memberProfileRepository.findAll(pageable).getContent();
        if (allMemberProfilesFromDb.size() == 0)
            return null;

        return allMemberProfilesFromDb.stream()
                .map(memberProfileEntity -> modelMapper.map(memberProfileEntity, MemberProfileDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MemberProfileDto getById(Long id) {

        if (!loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        MemberProfileEntity memberProfileFromDb = memberProfileRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Profile not found with id: " + id));

        return modelMapper.map(memberProfileFromDb, MemberProfileDto.class);
    }

    @Override
    public MemberProfileDto updateById(Long id, MemberProfileDto updatedRequestBody) {
        return null;
    }

    @Override
    public void deleteById() {

    }

    private boolean loggedInUserIsAdmin() {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        String roleName = loggedInMember.getRole().getRoleName();

        return roleName.equalsIgnoreCase("ADMIN");
    }
}
