package com.azad.cineplex2.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Director extends MoviePersonnel {

	private static final long serialVersionUID = 1L;

	@ManyToMany(mappedBy = "directors", fetch = FetchType.LAZY)
	protected List<Movie> movies;
}
