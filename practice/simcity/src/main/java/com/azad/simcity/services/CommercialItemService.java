package com.azad.simcity.services;

import java.util.List;

import com.azad.simcity.entities.CommercialItem;

public interface CommercialItemService {

	List<CommercialItem> getCommercialItems();

	CommercialItem createCommercialItem(CommercialItem commercialItem);

}
