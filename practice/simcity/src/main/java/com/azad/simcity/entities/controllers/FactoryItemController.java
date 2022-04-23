package com.azad.simcity.entities.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.azad.simcity.entities.FactoryItem;
import com.azad.simcity.services.FactoryItemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path = "/factoryItems")
public class FactoryItemController {

	private FactoryItemService service;

	@Autowired
	public FactoryItemController(FactoryItemService service) {
		super();
		this.service = service;
	}
	
	@GetMapping
	public String listFactoryItems(Model model) {
		
		List<FactoryItem> factoryItems = service.getFactoryItems();
		
		if (factoryItems != null) {
			model.addAttribute("factoryItems", factoryItems);
		}
		
		return "factory_items";
	}
	
	@PostMapping()
	public String saveFactoryItem(FactoryItem factoryItem) {
		FactoryItem item = service.createFactoryItem(factoryItem);
		return "redirect:/factoryItems";
	}
}
