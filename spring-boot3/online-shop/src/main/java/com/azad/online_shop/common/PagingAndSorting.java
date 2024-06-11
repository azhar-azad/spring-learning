package com.azad.online_shop.common;

public record PagingAndSorting(
        int page,
        int limit,
        String sort,
        String order
) {
}
