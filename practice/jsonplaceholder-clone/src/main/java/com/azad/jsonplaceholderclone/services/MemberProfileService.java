package com.azad.jsonplaceholderclone.services;

import com.azad.jsonplaceholderclone.models.dtos.MemberProfileDto;

public interface MemberProfileService extends GenericApiService<MemberProfileDto> {
    MemberProfileDto getLoggedInProfile();
}
