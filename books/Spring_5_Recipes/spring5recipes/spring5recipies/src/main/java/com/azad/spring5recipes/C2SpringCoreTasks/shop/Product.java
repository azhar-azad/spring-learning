/**
 * Suppose you’re going to develop a shop application to sell products online. First, you create the Product
 * POJO class, which has several properties, such as the product name and price. As there are many types of
 * products in your shop, you make the Product class abstract to extend it for different product subclasses.
 */

package com.azad.spring5recipes.C2SpringCoreTasks.shop;

public abstract class Product {
	
	private String name;
	private double price;
	
	public Product() {
	}

	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String toString() {
		return name + " " + price;
	}
	
	
}
