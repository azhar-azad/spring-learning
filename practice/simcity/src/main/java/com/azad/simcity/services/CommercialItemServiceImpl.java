package com.azad.simcity.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azad.simcity.entities.CommercialItem;
import com.azad.simcity.repos.CommercialItemRepository;

@Service
public class CommercialItemServiceImpl implements CommercialItemService {
	
	private CommercialItemRepository repository;
	
	@Autowired
	public CommercialItemServiceImpl(CommercialItemRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	public List<CommercialItem> getCommercialItems() {
		
		List<CommercialItem> commercialItems = repository.findAll();
		
		if (commercialItems == null || commercialItems.isEmpty() || commercialItems.size() == 0) {
			return null;
		}
		
		return commercialItems;
	}



	@Override
	public CommercialItem createCommercialItem(CommercialItem commercialItem) {
		CommercialItem item = repository.save(commercialItem);
		
		if (item == null) {
			throw new RuntimeException("item is null");
		}
		
		return item;
	}

}
