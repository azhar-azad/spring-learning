package com.azad.simcity.entities.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path = "/")
public class ViewController {

	public String homePage() {
		return "index";
	}
}
