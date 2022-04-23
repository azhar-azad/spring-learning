package com.azad.simcity.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azad.simcity.entities.FactoryItem;
import com.azad.simcity.repos.FactoryItemRepository;

@Service
public class FactoryItemServiceImpl implements FactoryItemService {

	private FactoryItemRepository repository;

	@Autowired
	public FactoryItemServiceImpl(FactoryItemRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public FactoryItem createFactoryItem(FactoryItem factoryItem) {

		FactoryItem item = repository.save(factoryItem);
		
		if (item == null) {
			throw new RuntimeException("item is null");
		}
		
		return item;
	}

	@Override
	public List<FactoryItem> getFactoryItems() {
		
		List<FactoryItem> factoryItems = repository.findAll();
		
		if (factoryItems == null || factoryItems.isEmpty() || factoryItems.size() == 0) {
			return null;
		}
		
		return factoryItems;
	}

}
