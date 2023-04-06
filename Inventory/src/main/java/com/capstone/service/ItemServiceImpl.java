package com.capstone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.entity.Item;
import com.capstone.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * Adds an item to the inventory. If an item with the same name, price, category, and cost price already exists,
	 * its quantity will be updated instead. Returns 0 if a new item was added, 1 if an existing item was updated.
	 */
	@Override
	public int addItem(Item item) {
		int exists = itemRepository.checkIfItemExists(item.getName(), item.getPrice(), item.getCategory(), item.getCostPrice());

		if (exists > 0) {
			int id = itemRepository.findId(item.getName(), item.getPrice(), item.getCategory(), item.getCostPrice());
			itemRepository.updateQuantity(id, item.getQuantity());
			return 1;
		} else {
			itemRepository.save(item);
			return 0;
		}
	}

	//Retrieves all items in the inventory.
	@Override
	public List<Item> getInventory() {
		return itemRepository.findAll();
	}

	//Retrieves all items in the inventory that belong to the specified category.
	@Override
	public List<Item> getItemsByCategory(String category) {
		return itemRepository.findByCategory(category);
	}

	/**
	 * Deletes an item from the inventory. If the item exists and its quantity is greater than or equal to the
	 * specified quantity, its quantity will be decremented by the specified quantity. If its quantity becomes zero,
	 * the item will be deleted from the inventory. Returns 1 if the item was deleted, 2 if its quantity was updated,
	 * 0 if its quantity could not be updated because the specified quantity was too large, and -1 if the item
	 * does not exist in the inventory.
	 */
	@Override
	public int deleteItem(String name, int quantity) {
		Optional<Item> item = itemRepository.findByName(name);

		if (item.isPresent() && item.get().getQuantity() > -(quantity)) {
			itemRepository.updateQuantity(item.get().getId(), quantity);
			return 2;
		} else if (item.isPresent() && item.get().getQuantity() == -(quantity)) {
			itemRepository.deleteById(item.get().getId());
			return 1;
		} else if (item.isPresent() && item.get().getQuantity() < -(quantity)) {
			return 0;
		} else {
			return -1;
		}
	}

	// Retrieves the item with the specified ID.
	@Override
	public Item findById(int id) {
		Optional<Item> item = itemRepository.findById(id);
		if(item.isPresent()) {
			return item.get();
		} else {
			return null; 
		}
		
	}

	//Retrieves the item with the specified name.
	@Override
	public Item findByName(String name) {
		Optional<Item> item = itemRepository.findByName(name);
		if(item.isPresent()) {
			return item.get();
		} else {
			return null; 
		}
	}

}
