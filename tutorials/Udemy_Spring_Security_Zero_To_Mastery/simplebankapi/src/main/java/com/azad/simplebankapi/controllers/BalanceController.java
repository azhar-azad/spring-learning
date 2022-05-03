package com.azad.simplebankapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/balances")
public class BalanceController {

	@GetMapping
	public String getBalanceDetails() {
		return "Here are the balance details from the Database";
	}
}
