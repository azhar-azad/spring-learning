package com.azad.cineplex.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Movie title cannot be null")
	private String title;
	
	@Size(min = 4, max = 4, message = "Year format should be YYYY")
	private int releaseYear;
	
	@Size(max = 200, message = "Summary should be no more than 200 characters")
	private String summary;
	
	@ManyToMany
	@JoinTable(
			name = "movie_genre",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<Genre> genres;
	
	@ManyToMany
	@JoinTable(
			name = "movie_director",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "director_id"))
	private List<Director> directors;
	
	@ManyToMany
	@JoinTable(
			name = "movie_cast",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "cast_id"))
	private List<Cast> casts;
	
	@ManyToMany
	@JoinTable(
			name = "movie_producer",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "producer_id"))
	private List<Producer> producers;
	
	@OneToOne(mappedBy = "movie")
	private Production production;
	
	@OneToOne(mappedBy = "movie")
	private Distributor distributor;
	
	@OneToOne(mappedBy = "movie")
	private Show show;
}
