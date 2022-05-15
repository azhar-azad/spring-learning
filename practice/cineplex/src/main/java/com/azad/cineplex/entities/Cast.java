package com.azad.cineplex.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cast extends MoviePersonnel {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToMany(mappedBy = "casts")
	private List<Movie> movies;
}
