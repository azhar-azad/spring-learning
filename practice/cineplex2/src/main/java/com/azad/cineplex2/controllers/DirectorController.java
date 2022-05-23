package com.azad.cineplex2.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azad.cineplex2.services.DirectorService;

@RestController
@RequestMapping(path = "/api/v1/directors")
public class DirectorController {

	private ModelMapper modelMapper;
	private DirectorService directorService;
	
	@Autowired
	public DirectorController(ModelMapper modelMapper, DirectorService directorService) {
		this.modelMapper = modelMapper;
		this.directorService = directorService;
	}
	
	
}
