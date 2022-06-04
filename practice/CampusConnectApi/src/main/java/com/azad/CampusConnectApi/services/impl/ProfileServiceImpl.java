package com.azad.CampusConnectApi.services.impl;

import com.azad.CampusConnectApi.exceptions.ResourceCreationFailedException;
import com.azad.CampusConnectApi.exceptions.ResourceNotFoundException;
import com.azad.CampusConnectApi.models.Profile;
import com.azad.CampusConnectApi.models.dtos.ProfileDto;
import com.azad.CampusConnectApi.models.entities.AppUserEntity;
import com.azad.CampusConnectApi.models.entities.ProfileEntity;
import com.azad.CampusConnectApi.repositories.AppUserRepository;
import com.azad.CampusConnectApi.repositories.ProfileRepository;
import com.azad.CampusConnectApi.services.ProfileService;
import com.azad.CampusConnectApi.utils.PagingAndSortingObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUserRepository appUserRepository;

    private ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileDto create(ProfileDto requestDto) {

        Long appUserId = requestDto.getAppUserId();

        // Check if already has a profile for this user
        ProfileEntity existingProfile = profileRepository.findByAppUserId(appUserId);
        if (existingProfile != null) {
            throw new ResourceCreationFailedException("This AppUser already has a profile with id: " + existingProfile.getId());
        }

        // No profile already stored. We can create a profile now
        // Get the AppUser by appUserId
        AppUserEntity appUser = appUserRepository.findById(appUserId).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", appUserId));

        // Set the AppUser to Profile for Entity mapping (1-to-1)
        ProfileEntity profile = modelMapper.map(requestDto, ProfileEntity.class);
        profile.setAppUser(appUser);
        profile.setHobbies(String.join(",", requestDto.getHobbies()));

        // Get the DTO object for data transfer
        ProfileDto dto = modelMapper.map(profileRepository.save(profile), ProfileDto.class);
        dto.setAppUserId(requestDto.getAppUserId());
        dto.setHobbies(Arrays.asList(profile.getHobbies().split(",")));

        return dto;
    }

    @Override
    public ProfileDto getProfileByAppUserId(Long appUserId) {

        ProfileEntity profile = profileRepository.findByAppUserId(appUserId);

        if (profile == null) {
            throw new ResourceNotFoundException("Profile", "app_user_id");
        }

        ProfileDto dto = modelMapper.map(profile, ProfileDto.class);
        dto.setAppUserId(appUserId);
        dto.setHobbies(Arrays.asList(profile.getHobbies().split(",")));

        return dto;
    }

    @Override
    public ProfileDto updateProfileByAppUserId(Long appUserId, ProfileDto updatedProfileDto) {

        ProfileEntity profile = profileRepository.findByAppUserId(appUserId);

        if (updatedProfileDto.getStudyingAt() != null)
            profile.setStudyingAt(updatedProfileDto.getStudyingAt());
        if (updatedProfileDto.getHomePlace() != null)
            profile.setHomePlace(updatedProfileDto.getHomePlace());
        if (updatedProfileDto.getLivesIn() != null)
            profile.setLivesIn(updatedProfileDto.getLivesIn());
        if (updatedProfileDto.getBio() != null)
            profile.setBio(updatedProfileDto.getBio());
        if (updatedProfileDto.getHobbies() != null)
            profile.setHobbies(String.join(",", updatedProfileDto.getHobbies()));
        if (updatedProfileDto.getRelationshipStatus() != null)
            profile.setRelationshipStatus(updatedProfileDto.getRelationshipStatus());
        if (updatedProfileDto.getPoliticalView() != null)
            profile.setPoliticalView(updatedProfileDto.getPoliticalView());

        ProfileDto dto = modelMapper.map(profileRepository.save(profile), ProfileDto.class);
        dto.setAppUserId(appUserId);
        dto.setHobbies(Arrays.asList(profile.getHobbies().split(",")));

        return dto;
    }

    @Override
    public void deleteProfileByAppUserId(Long appUserId) {

        ProfileEntity profile = profileRepository.findByAppUserId(appUserId);

        profileRepository.delete(profile);
    }

    @Override
    public List<ProfileDto> getAll(PagingAndSortingObject ps) {
        return null;
    }

    @Override
    public ProfileDto getById(Long id) {
        return null;
    }

    @Override
    public ProfileDto updateById(Long id, ProfileDto updatedRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
