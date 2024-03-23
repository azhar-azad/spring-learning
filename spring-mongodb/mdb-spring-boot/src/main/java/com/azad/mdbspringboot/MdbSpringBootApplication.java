package com.azad.mdbspringboot;

import com.azad.mdbspringboot.model.GroceryItem;
import com.azad.mdbspringboot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MdbSpringBootApplication implements CommandLineRunner {

	@Autowired
	ItemRepository groceryItemRepo;

	public static void main(String[] args) {
		SpringApplication.run(MdbSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("-----CREATE GROCERY ITEMS-----\n");
		// create
		createGroceryItems();

		System.out.println("\n-----SHOW ALL GROCERY ITEMS-----\n");
		// 1. Show all the data
		showAllGroceryItems();

		System.out.println("\n-----GET ITEM BY NAME-----\n");
		// 2. Get item by name
		getGroceryItemByName("Whole Wheat Biscuit");

		System.out.println("\n-----GET ITEMS BY CATEGORY-----\n");
		// 3. Get name and quantity of all items of a particular category
		getItemsByCategory("millets");

		System.out.println("\n-----UPDATE CATEGORY NAME OF SNACKS CATEGORY-----\n");
		// 5. Update category name to a new value
		updateCategoryName("snacks", "munchies");

		System.out.println("\n-----DELETE A GROCERY ITEM-----\n");
		deleteGroceryItem("Kodo Millet");

		System.out.println("\n-----FINAL COUNT OF GROCERY ITEMS-----\n");
		// 4. Get count of documents in the collection
		findCountOfGroceryItems();

		System.out.println("\n-----THANK YOU-----");
	}

	void createGroceryItems() {
		System.out.println("Data creation started...");
		groceryItemRepo.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
		groceryItemRepo.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
		groceryItemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
		groceryItemRepo.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
		groceryItemRepo.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
		System.out.println("Data creation complete...");
	}

	public void showAllGroceryItems() {
		groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
	}

	public void getGroceryItemByName(String name) {
		System.out.println("Getting item by name: " + name);
		GroceryItem item = groceryItemRepo.findItemByName(name);
		System.out.println(getItemDetails(item));
	}

	public void getItemsByCategory(String category) {
		System.out.println("Getting items for the category: " + category);
		List<GroceryItem> list = groceryItemRepo.findAll(category);

		list.forEach(item -> System.out.println("Name: " + item.getName() +
				", Quantity: " + item.getQuantity()));
	}

	public void findCountOfGroceryItems() {
		long count = groceryItemRepo.count();
		System.out.println("Number of documents in the collection: " + count);
	}

	public void updateCategoryName(String category, String newCategory) {

		// Find all the items with the category snacks
		List<GroceryItem> list = groceryItemRepo.findAll(category);

		list.forEach(item -> {
			// Update the category in each document
			item.setCategory(newCategory);
		});

		// Save all the items in database
		List<GroceryItem> itemsUpdated = groceryItemRepo.saveAll(list);

		if (itemsUpdated != null)
			System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
	}

	public void deleteGroceryItem(String id) {
		groceryItemRepo.deleteById(id);
		System.out.println("Item with id " + id + " deleted...");
	}

	// Print details in readable form
	private String getItemDetails(GroceryItem item) {
		System.out.println("Item Name: " + item.getName() +
				", \nQuantity: " + item.getQuantity() +
				", \nItem Category: " + item.getCategory());
		return "";
	}
}
