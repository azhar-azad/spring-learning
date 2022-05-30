package com.azad.bankapi.models;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankUser {

	@NotNull(message = "First name cannot be empty")
	private String firstName;
	
	@NotNull(message = "Last name cannot be empty")
	private String lastName;
	
	@NotNull(message = "Email cannot be empty")
	@Email(message = "Email has to be valid")
	private String email;
	
	@NotNull(message = "Phone number cannot be empty")
	private String phoneNumber;
	
	@NotNull(message = "Date of birth cannot be empty")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dateOfBirth;
}
