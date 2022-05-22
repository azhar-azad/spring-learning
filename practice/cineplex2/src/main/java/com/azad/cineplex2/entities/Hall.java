package com.azad.cineplex2.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueNameAndSerialNo", columnNames = { "name", "serialNo" }) })
public class Hall implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String serialNo;
	
	@Column(nullable = false)
	private int capacity;
	
	private int floor;
	
//	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonIgnoreProperties("hall")
	private List<Show> shows;
	
}
