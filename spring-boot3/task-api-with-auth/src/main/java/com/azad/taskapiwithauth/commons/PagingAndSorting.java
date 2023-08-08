package com.azad.taskapiwithauth.commons;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagingAndSorting {

    private int page;
    private int limit;
    private String sort;
    private String order;
}
