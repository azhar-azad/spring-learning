package com.azad.taskapiwithauth.commons.generics;

import com.azad.taskapiwithauth.commons.PagingAndSorting;

import java.util.List;

public interface GenericApiService<T> {

    T create(T dto);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id, T updatedDto);
    void deleteById(Long id);
}
