package com.azad.jsonplaceholderclone.services;

import com.azad.jsonplaceholderclone.utils.PagingAndSorting;

import java.util.List;

public interface GenericApiService<T> {

    T create(T requestBody);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id, T updatedRequestBody);
    void deleteById(Long id);
}
