package com.azad.worldcup22api.services;

import com.azad.worldcup22api.utils.PagingAndSorting;

public interface GenericApiService<T> {

    T create(T reqBody);
    T getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id, T updatedReqBody);
    void deleteById(Long id);
}
