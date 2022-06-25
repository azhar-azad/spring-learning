package com.azad.todolist.services;

import com.azad.todolist.models.dtos.AppUserDto;

import java.util.List;

public interface AppUserService extends ApiService<AppUserDto> {
    AppUserDto getByEmail(String email);

    List<AppUserDto> getAll();
}
