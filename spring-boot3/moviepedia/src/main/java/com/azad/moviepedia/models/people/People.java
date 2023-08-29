package com.azad.moviepedia.models.people;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class People {

    protected String fullName;
    protected LocalDate birthDate;

//    @NotNull(message = "Role in the movie cannot be null")
//    @Enumerated(EnumType.STRING)
//    protected PeopleRole role;


}
