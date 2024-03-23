package com.azad.mdbspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("groceryitems")
public class GroceryItem {

    @Id
    private String id;

    private String name;
    private int quantity;
    private String category;


}
