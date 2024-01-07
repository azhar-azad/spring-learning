package com.azad.tacocloud.tacos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * TacoOrder class is needed to define how customers specify the tacos that they want to order, along with payment and
 * delivery information.
 *
 * @CreditCardNumber - annotation declares that the property's value must be a valid credit card number that passes
 * the Luhn algorithm check.
 * @Digits - ensures that the value contains exactly three numeric digits.
 *
 * We'll need to annotate our domain classes so that Spring Data JDBC will know how to persist them.
 * @Table - This annotation is completely optional. By default, the object is mapped to a table based on the domain
 * class name. In this case, TacoOrder is mapped to a table named "taco_order". If we'd prefer to map it to a different
 * table name, then we can specify the table name as parameter to @Table like this -
 *      @Table("Taco_Cloud_Order")
 * @Id - It designates the id property as being the identity for a TacoOrder.
 * All other properties in TacoOrder will be mapped automatically to columns based on their property names. For example,
 * the deliveryName property will be automatically mapped to the column named delivery_name. But if we want to
 * explicitly define the column name mapping, we could annotate the property with @Column like this:
 *      @Column("customer_name")
 */
@Data
@Table
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Date placedAt;

    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])(/)([2-9][0-9])$", message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
