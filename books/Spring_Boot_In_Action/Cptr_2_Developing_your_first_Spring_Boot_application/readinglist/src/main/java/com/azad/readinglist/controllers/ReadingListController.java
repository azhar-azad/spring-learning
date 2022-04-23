/**
 * @ConfigurationProperties
 * This specifies that this bean should have its properties injected (via setter 
 * methods) with values from configuration properties. More specifically, the prefix 
 * attribute specifies that the ReadingListController bean will be injected with properties  
 * with an “amazon” prefix.
 * */

package com.azad.readinglist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.azad.readinglist.entities.Book;
import com.azad.readinglist.repos.ReadingListRepository;

@Controller
@RequestMapping("/")
@ConfigurationProperties(prefix = "amazon") // Inject with properties
public class ReadingListController {
	
	private String amazonAssociateId;

	private ReadingListRepository readingListRepository;

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository) {
		this.readingListRepository = readingListRepository;
	}
	
	public void setAmazonAssociateId(String amazonAssociateId) {  // Setter method for amazonAssociateId
		this.amazonAssociateId = amazonAssociateId;
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String readersBooks(@PathVariable("reader") String reader, Model model) {

		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
			model.addAttribute("amazonID", amazonAssociateId);  // Put associateId into model
		}
		return "readingList";
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader, Book book) {

		book.setReader(reader);
		readingListRepository.save(book);

		return "redirect:/{reader}";
	}
}
