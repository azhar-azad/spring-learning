package com.azad.school.services;

import com.azad.school.utils.PagingAndSorting;

import java.util.List;

public interface ApiService<T> {

    T create(T requestDto);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id);
    void deleteById(Long id);
}
