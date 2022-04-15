/**
 * Subclass Battery with its own properties.
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.shop;

public class Battery extends Product {

	private boolean rechargeable;

	public Battery() {
		super();
	}

	public Battery(String name, double price) {
		super(name, price);
	}

	public boolean isRechargeable() {
		return rechargeable;
	}

	public void setRechargeable(boolean rechargeable) {
		this.rechargeable = rechargeable;
	}

}
