/**
 * @Table
 * The @Table annotation is completely optional. By default, the object is mapped to a 
 * table based on the domain class name. In this case, TacoOrder is mapped to a table 
 * named "Taco_Order".
 * 
 * As for the @Id annotation, it designates the id property as being the identity for a 
 * TacoOrder. All other properties in TacoOrder will be mapped automatically to columns 
 * based on their property names. For example, the deliveryName property will be 
 * automatically mapped to the column named delivery_name. But if you want to 
 * explicitly define the column name mapping, you could annotate the property with 
 * @Column like this @Column("customer_name").
 * */

package tacos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
@Entity
public class TacoOrder {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
//	@CreditCardNumber(message = "Not a valid credit card number") // commented out for testing purpose
	@NotBlank(message = "Credit Card Number is required")
	private String ccNumber;
	
	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;
	
	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String ccCVV;

	@OneToMany(cascade = CascadeType.ALL) // if the order is deleted, its related tacos will also be deleted
	private List<Taco> tacos = new ArrayList<>();

	public void addTaco(Taco taco) {
		this.tacos.add(taco);
	}
}
