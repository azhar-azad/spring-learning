package com.azad.todolist.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagingAndSorting {

    private int page;
    private int limit;
    private String sort;
    private String order;
}
