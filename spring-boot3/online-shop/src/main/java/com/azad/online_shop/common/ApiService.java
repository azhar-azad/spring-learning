package com.azad.online_shop.common;

import java.util.List;

public interface ApiService <T>{

    T create(T dto);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id, T updatedDto);
    void deleteById(Long id);
}
