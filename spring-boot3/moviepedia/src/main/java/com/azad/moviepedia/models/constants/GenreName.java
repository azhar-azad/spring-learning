package com.azad.moviepedia.models.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GenreName {

    ACTION("action"),
    ADVENTURE("adventure"),
    ANIMATION("animation"),
    ANIME("anime"),
    BIOGRAPHY("biography"),
    COMEDY("comedy"),
    CRIME("crime"),
    DOCUMENTARY("documentary"),
    DRAMA("drama"),
    FAMILY("family"),
    FANTASY("fantasy"),
    HISTORY("history"),
    HORROR("horror"),
    MUSICAL("musical"),
    MYSTERY("mystery"),
    ROMANCE("romance"),
    SCI_FI("sci-fi"),
    SPORT("sport"),
    SUPERHERO("superhero"),
    SURVIVAL("survival"),
    THRILLER("thriller"),
    WAR("war"),
    WESTERN("western"),
    UNDEFINED("undefined");

    private final String value;

    GenreName(String value) {
        this.value = value;
    }

    private static Map<String, GenreName> FORMAT_MAP = Stream.of(GenreName.values())
            .collect(Collectors.toMap(g -> g.value, Function.identity()));

    @JsonCreator
    public static GenreName fromValue(String value) {
        return Optional.ofNullable(FORMAT_MAP.get(value))
                .orElse(UNDEFINED);
    }
}
