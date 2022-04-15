package com.azad.spring5recipes.C3SpringMVC.court.domain;

public class SportType {

	private int id;
	private String name;

	public SportType() {
	}

	public SportType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
