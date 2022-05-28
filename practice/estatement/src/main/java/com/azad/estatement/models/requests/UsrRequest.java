package com.azad.estatement.models.requests;

import javax.validation.constraints.NotNull;

import com.azad.estatement.models.Usr;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsrRequest extends Usr {

	@NotNull(message = "Cif num cannot be empty")
	private String cifNum;
}
