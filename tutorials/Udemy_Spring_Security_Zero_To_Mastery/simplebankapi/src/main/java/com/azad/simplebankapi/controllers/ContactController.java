package com.azad.simplebankapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/contacts")
public class ContactController {

	@GetMapping
	public String saveContactInquiryDetails() {
		return "Inquiry details are saved to the Database";
	}
}
