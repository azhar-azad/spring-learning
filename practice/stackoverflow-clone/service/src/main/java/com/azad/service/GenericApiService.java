package com.azad.service;

import com.azad.common.PagingAndSorting;

import java.util.List;

/***
 * A generic service interface to provide the CRUD functionalities for an Entity.
 *
 *      create:
 *          - Receive the DTO class as argument.
 *          - Extract the Entity data from DTO.
 *          - Save the Entity.
 *          - Convert to DTO and return.
 *      getAll:
 *          - Return all the Entities from database.
 *          - Pagination functionalities.
 *          - PagingAndSorting class will contain the page, limit, sort and order from request.
 *      getById:
 *          - Receive the Entity ID passed in request.
 *          - Retrieve the Entity from database by ID.
 *          - If not found, throw an error that will be resolved by global exception handler.
 *          - If found, convert to DTO and return.
 *      updateById:
 *          - Receive the Entity ID and DTO class with updated values from request.
 *          - Retrieve the Entity from database by ID.
 *          - If not found, throw an error that will be resolved by global exception handler.
 *          - If found, update the Entity field(s) by the updated values from DTO.
 *          - Save the Entity.
 *          - Convert to DTO and return.
 *      deleteById:
 *          - Receive the Entity ID passed in request.
 *          - Retrieve the Entity from database by ID.
 *          - If not found, throw an error that will be resolved by global exception handler.
 *          - If found, delete from database and return nothing.
 *
 * @param <T> Entity DTO class that will resolve the data transfers between classes.
 */
public interface GenericApiService<T> {

    T create(T dto);
    List<T> getAll(PagingAndSorting ps);
    T getById(Long id);
    T updateById(Long id, T updatedDto);
    void deleteById(Long id);
}
