package com.azad.simcity.services;

import java.util.List;

import com.azad.simcity.entities.FactoryItem;

public interface FactoryItemService {

	FactoryItem createFactoryItem(FactoryItem factoryItem);

	List<FactoryItem> getFactoryItems();

}
