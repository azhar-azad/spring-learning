/**
 * Subclass Disc with its own properties.
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.shop;

public class Disc extends Product {

	private int capacity;

	public Disc() {
		super();
	}

	public Disc(String name, double price) {
		super(name, price);
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
