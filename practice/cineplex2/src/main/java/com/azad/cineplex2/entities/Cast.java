package com.azad.cineplex2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cast extends MoviePersonnel {

	private static final long serialVersionUID = 1L;

	@Column
	private String stageName;
}
