package com.azad.tacos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 * This is a simple Java domain class, defining the three properties needed to describe an ingredient.
 *
 * @Entity - To declare this as a JPA entity, Ingredient must be annotated with @Entity. And its id property must be
 * annotated with @Id to designate it as the property that will uniquely identify the entity in the database.
 * @NoArgsConstructor - JPA requires that entities have a no-argument constructor, so Lombok's @NoArgsConstructor
 * does that for us. We make it private with AccessLevel.PRIVATE. And because we must set final properties, we also set
 * the force attribute to true, which results in the Lombok-generated constructor setting them to a default value of
 * null, 0, or false, depending on the property type.
 * @AllArgsConstructor - We also added an @AllArgsConstructor annotation to make it easy to create an Ingredient object
 * with all properties initialized.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
