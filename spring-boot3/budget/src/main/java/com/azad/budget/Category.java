package com.azad.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
public class Category {

    private String name;
    private Type type;
    private Double amount;
    private Double totalAmount;
    private Set<SubCategory> subCategories;
}
