package com.azad.simplebankapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/cards")
public class CardsController {

	@GetMapping
	public String getCardDetails() {
		return "Here are the card details from the Database";
	}
}
