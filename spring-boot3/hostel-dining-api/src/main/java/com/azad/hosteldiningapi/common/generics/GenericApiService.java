package com.azad.hosteldiningapi.common.generics;

import com.azad.hosteldiningapi.common.PagingAndSorting;

import java.util.List;

public interface GenericApiService<T> {

    T create(T dto);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T getByUid(String uid);
    T updateById(Long id, T updatedDto);
    T updateByUid(String uid, T updatedDto);
    void deleteById(Long id);
    void deleteByUid(String uid);
}
