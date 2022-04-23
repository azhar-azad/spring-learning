package com.azad.simcity.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class CommercialItem extends Item {

	@Transient
	private List<Item> requiredItems;
	private String shopName;

}
