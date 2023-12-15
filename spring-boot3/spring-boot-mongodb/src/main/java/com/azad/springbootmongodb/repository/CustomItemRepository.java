package com.azad.springbootmongodb.repository;

public interface CustomItemRepository {

    void updateItemQuantity(String name, float newQuantity);
}
