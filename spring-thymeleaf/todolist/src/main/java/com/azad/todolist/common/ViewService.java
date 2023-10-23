package com.azad.todolist.common;

import java.util.List;

public interface ViewService<T> {

    void save(T entity);
    List<T> getAll();
    T getById(Long id);
    void deleteById(Long id);
}
