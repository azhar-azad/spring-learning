package com.azad.todolist.services;

import com.azad.todolist.utils.PagingAndSorting;

public interface ApiService<T> {

    T create(T requestDto);
    T getAll(PagingAndSorting ps);
    T getByEntityId(String entityId);
    T updateByEntityId(String entityId, T updatedDto);
    void deleteByEntityId(String entityId);
}
