package com.azad.onlineed.services;

import com.azad.onlineed.utils.PagingAndSorting;

import java.util.List;

public interface ApiService<T> {

    T create(T request);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id, T updatedRequest);
    void deleteById(Long id);
}
