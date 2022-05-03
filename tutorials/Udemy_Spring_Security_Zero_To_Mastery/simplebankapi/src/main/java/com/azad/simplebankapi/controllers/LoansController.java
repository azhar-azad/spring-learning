package com.azad.simplebankapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/loans")
public class LoansController {

	@GetMapping
	public String getLoanDetails() {
		return "Here are the loan details from the Database";
	}
}
