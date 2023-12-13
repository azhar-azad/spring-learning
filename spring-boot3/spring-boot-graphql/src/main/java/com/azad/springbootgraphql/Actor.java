package com.azad.springbootgraphql;

import java.util.List;

public record Actor(
        int id,
        String name,
        String birthDate,
        List<Movie> movies
) {
}
