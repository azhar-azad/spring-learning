package com.azad.mdbspringboot.repository;

import com.azad.mdbspringboot.model.GroceryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<GroceryItem, String> {

    /*
    findItemByName requires a parameter for the query, i.e. the field by which
    to filter the query. We specify this with the annotation @Query.
     */
    @Query("{name: '?0'}")
    GroceryItem findItemByName(String name);

    /*
    findAll uses the category field to get all the items of a particular category.
    We only want to project the field's name and quantity in the query response,
    so we set those fields to 1.
     */
    @Query(value = "{category: '?0'}", fields = "{'name': 1, 'quantity': 1}")
    List<GroceryItem> findAll(String category);

    /*
    We reuse the method count() as it is.
     */
    long count();
}
