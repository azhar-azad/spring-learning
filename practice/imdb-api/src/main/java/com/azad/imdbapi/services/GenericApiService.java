package com.azad.imdbapi.services;

import com.azad.imdbapi.utils.PagingAndSorting;

import java.util.List;

public interface GenericApiService<T> {

    T create(T request);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id, T updatedRequest);
    void deleteById(Long id);
}
