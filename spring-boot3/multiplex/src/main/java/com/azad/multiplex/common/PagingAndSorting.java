package com.azad.multiplex.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagingAndSorting {

    private int page;
    private int limit;
    private String sort;
    private String order;
}
