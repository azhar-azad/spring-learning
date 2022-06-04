package com.azad.CampusConnectApi.services;

import com.azad.CampusConnectApi.models.dtos.ProfileDto;

public interface ProfileService extends GenericApiService<ProfileDto> {
    ProfileDto getProfileByAppUserId(Long appUserId);

    ProfileDto updateProfileByAppUserId(Long appUserId, ProfileDto updatedProfileDto);

    void deleteProfileByAppUserId(Long appUserId);
}
