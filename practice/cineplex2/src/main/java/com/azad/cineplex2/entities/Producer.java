package com.azad.cineplex2.entities;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Producer extends MoviePersonnel {

	private static final long serialVersionUID = 1L;
}
