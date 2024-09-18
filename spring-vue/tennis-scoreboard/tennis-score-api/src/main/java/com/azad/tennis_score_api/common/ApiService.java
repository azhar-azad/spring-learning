package com.azad.tennis_score_api.common;

import java.util.List;

public interface ApiService<DTO> {

    DTO create(DTO dto) throws RuntimeException;
    List<DTO> getAll(PagingAndSorting ps) throws RuntimeException;
    DTO getById(Long id) throws RuntimeException;
    DTO updateById(Long id, DTO updatedDto) throws RuntimeException;
    void deleteById(Long id) throws RuntimeException;
}
