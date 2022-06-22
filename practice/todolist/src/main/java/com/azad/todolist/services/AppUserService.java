package com.azad.todolist.services;

import com.azad.todolist.models.dtos.AppUserDto;

public interface AppUserService extends ApiService<AppUserDto> {
    AppUserDto getByEmail(String email);
}
