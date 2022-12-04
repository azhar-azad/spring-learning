package com.azad.wcstatapi.services;

import com.azad.wcstatapi.models.dtos.MatchDto;

public interface MatchService extends GenericApiService<MatchDto> {

    MatchDto updateByMatchNo(Integer matchNo, MatchDto updatedRequest);
}
