package com.azad.data.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/***
 * Many to many relation defined between AppUser and List.
 * The Entity class will contain the mapping between app_user_id and list_id.
 * Also, each row will contain the Access modifier.
 * Possible accesses are: READ, UPDATE, DELETE.
 * If access is DELETE, that user can also UPDATE and READ that list.
 * If access is UPDATE, that user can also READ that list.
 * If access is READ, that user can only READ that list.
 */
@Data
@NoArgsConstructor
public class Access {

    @NotNull(message = "Must provide one of the access. READ/UPDATE/DELETE")
    protected String accessName;
}
