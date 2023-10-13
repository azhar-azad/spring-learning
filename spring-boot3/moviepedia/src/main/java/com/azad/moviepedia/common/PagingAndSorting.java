package com.azad.moviepedia.common;

import lombok.*;

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
