package com.azad.ecommerce.services;

import java.util.List;
import java.util.Map;

public interface GenericApiService<T> {

    T create(T request);
    List<T> getAll(Map<String, String> reqParams);
    T getById(Long id);
    T updateById(Long id, T updatedRequest);
    void deleteById(Long id);
}
