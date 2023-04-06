package com.capstone.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.capstone.entity.InventoryPDFExporter;
import com.capstone.entity.Item;
import com.capstone.service.ItemServiceImpl;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ItemController {

	@Autowired
	private ItemServiceImpl itemService;

	// Method for displaying the add item page
	@RequestMapping("/addItem")
	public ModelAndView addItemPageController() {
		return new ModelAndView("AddItem", "command1", new Item());
	}

	// Method for adding a new item to inventory and displaying a confirmation message
	@RequestMapping("/itemAdded")
	public ModelAndView itemAddedPageController(@ModelAttribute("command1") Item item) {
		ModelAndView modelAndView = new ModelAndView();
		int addItem = itemService.addItem(new Item(item.getId(), item.getPrice(), item.getQuantity(), item.getName(),
				item.getCategory(), item.getCostPrice(), item.getQuantitySold(), item.getPurchases()));
		String message = null;
		if (addItem > 0) {
			message = "Quantity updated";
		} else {
			message = "New item added to inventory";
		}

		modelAndView.addObject("message", message);
		modelAndView.setViewName("ItemAdded");
		return modelAndView;
	}

	/*
	 * This method takes two optional parameters "category" and "name"
	 * and returns a list of items matching the specified category or name. This
	 * provides flexibility to the user, allowing them to filter the inventory by
	 * category or search for a specific item by name.
	 */
	@RequestMapping("/home")
	public ModelAndView getInventory(@RequestParam(required = false) String category,
			@RequestParam(required = false) String name) {
		ModelAndView modelAndView = new ModelAndView();
		List<Item> inventory;
		if (category == null || category.isEmpty()) {
			inventory = itemService.getInventory();
		} else {
			inventory = itemService.getItemsByCategory(category);
		}
		modelAndView.addObject("items", inventory);
		modelAndView.addObject("categories",
				itemService.getInventory().stream().map(Item::getCategory).distinct().collect(Collectors.toList()));
		modelAndView.addObject("chosenCategory", category);

		if (name != null) {
			Item searchedItem = itemService.findByName(name);
			if (searchedItem != null) {
				modelAndView.addObject("searchedItem", searchedItem);
			} else {
				String errorMessage = "Item does not exist";
				modelAndView.addObject("errorMessage", errorMessage);
			}
		}

		modelAndView.setViewName("Home");
		return modelAndView;
	}

	@RequestMapping("/deleteItem")
	public ModelAndView deleteItemController() {
		return new ModelAndView("DeleteItem", "command3", new Item());
	}

	/*
	 * This method takes an Item object as a model attribute and uses it to delete
	 * an item from the inventory. It also sets a message to be displayed to the
	 * user based on the result of the operation. This method provides a simple way
	 * for users to delete items from the inventory.
	 */
	@RequestMapping("/itemDeleted")
	public ModelAndView itemDeletedController(@ModelAttribute("command3") Item item) {
		ModelAndView modelAndView = new ModelAndView();

		String message = null;
		if (itemService.deleteItem(item.getName(), -item.getQuantity()) > 0) {
			message = "Inventory updated";
		} else if (itemService.deleteItem(item.getName(), -item.getQuantity()) == 0) {
			message = "Quantity wanting to delete exceeds stock level. Try again";
		} else {
			message = "Item does not exist try again";
		}

		modelAndView.addObject("message", message);
		modelAndView.setViewName("ItemDeleted");
		return modelAndView;
	}

	/*
	 * This method exports the inventory to a PDF file and
	 * returns it as a response to the client. This method is useful for generating
	 * reports or exporting data in a format that can be easily shared and viewed by
	 * others.
	 */
	@RequestMapping("/exportInventory")
	public void exportInventoryToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		String headerKey = "InventoryContent";
		String headerValue = "download; filename = inventory.pdf";

		response.setHeader(headerKey, headerValue);
		List<Item> inventory = itemService.getInventory();
		InventoryPDFExporter inventoryExporter = new InventoryPDFExporter(inventory);
		inventoryExporter.exportToPDF(response);
	}

}
