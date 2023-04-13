package com.azad.authenticationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@Document(collation = "users")
public class AppUserEntity {

    @Id
    @Field(name = "user_id")
    private Long id;

    @Field(name = "first_name")
    @NotNull
    private String firstName;

    @Field(name = "last_name")
    @NotNull
    private String lastName;

    @Field(name = "email")
    @Indexed(unique = true)
    @NotNull
    private String email;

    @Field(name = "username")
    @Indexed(unique = true)
    @NotNull
    private String username;

    @Field(name = "password")
    @NotNull
    private String password;
}
