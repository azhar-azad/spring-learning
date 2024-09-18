package com.azad.tennis_score_api.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingAndSorting {

    private int page;
    private int limit;
    private String sort;
    private String order;
}
