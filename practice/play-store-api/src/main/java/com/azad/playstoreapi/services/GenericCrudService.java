package com.azad.playstoreapi.services;

import com.azad.playstoreapi.utils.PagingAndSorting;

import java.util.List;

public interface GenericCrudService<T> {

    T create(T request);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id, T updatedRequest);
    void deleteById(Long id);
}
