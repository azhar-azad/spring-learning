package com.azad.todolist.services;

import com.azad.todolist.utils.PagingAndSorting;

import java.util.List;

public interface ApiService<T> {

    T create(T requestDto);
    List<T> getAll(PagingAndSorting ps);
    T getByEntityId(Long entityId);
    T updateByEntityId(Long entityId, T updatedDto);
    void deleteByEntityId(Long entityId);
}
