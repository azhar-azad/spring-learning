package com.azad.data.models.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/***
 * Task to represent a single todo.
 */
@Data
@NoArgsConstructor
public class Task {

    @NotNull(message = "Task title cannot be empty.")
    @Size(min = 2, message = "Task title has to be more than 2 characters long.")
    protected String taskTitle;
    protected String taskDetails;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    protected LocalDate targetDate;

    @JsonProperty
    protected boolean isStarred;

    @JsonProperty
    protected boolean isPrivate;
}
