package com.azad.moviepedia.models.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Writer {

    @NotBlank(message = "Writer first name cannot be empty")
    protected String firstName;

    @NotBlank(message = "Writer last name cannot be empty")
    protected String lastName;

    @NotNull(message = "Writer birth date cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    protected LocalDate birthDate;

    protected String birthPlace;
    protected String miniBio;
    protected String age;
}
