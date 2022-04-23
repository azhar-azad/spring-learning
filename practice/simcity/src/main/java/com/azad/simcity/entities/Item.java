package com.azad.simcity.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	protected String name;
	protected String productionTime;
	protected int availabilityCount;
}
