package com.azad.budget;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Budget {

    private Set<Category> categories;
    private Double totalAmount;
    private Double totalNeedAmount;
    private Double totalWantAmount;
    private Double totalSavingAmount;
}
