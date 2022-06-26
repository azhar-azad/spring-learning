package com.azad.todolist.services;

import com.azad.todolist.models.dtos.AppUserDto;

import java.util.List;

public interface AppUserService extends ApiService<AppUserDto> {
    AppUserDto getByEmail(String email);

    List<AppUserDto> getSearchResult(String searchKey, String searchTerm);

    AppUserDto getByUserId(String userId);

    AppUserDto updateByUserId(String userId, AppUserDto appUserDto);

    void deleteByUserId(String userId);
}
