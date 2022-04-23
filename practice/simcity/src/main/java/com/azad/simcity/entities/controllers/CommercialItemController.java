package com.azad.simcity.entities.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.azad.simcity.entities.CommercialItem;
import com.azad.simcity.services.CommercialItemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path = "/commercialItems")
public class CommercialItemController {

	private CommercialItemService service;

	@Autowired
	public CommercialItemController(CommercialItemService service) {
		super();
		this.service = service;
	}
	
	@GetMapping
	public String listCommercialItems(Model model) {
		
		List<CommercialItem> commercialItems = service.getCommercialItems();
		
		if (commercialItems != null) {
			model.addAttribute("commercialItems", commercialItems);
		}
		
		return "commercial_items";
	}
	
	@PostMapping()
	public String saveCommercialItem(CommercialItem commercialItem) {
		CommercialItem item = service.createCommercialItem(commercialItem);
		return "redirect:/commercialItems";
	}
}
