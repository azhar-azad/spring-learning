package com.azad.musiclibrary.services;

import com.azad.musiclibrary.utils.PagingAndSortingObject;

import java.util.List;

public interface GenericApiService<T> {

    T create(T requestDto);
    List<T> getAll(PagingAndSortingObject ps);
    T getById(Long id);
    T updateById(Long id, T updatedRequestDto);
    void deleteById(Long id);
}
