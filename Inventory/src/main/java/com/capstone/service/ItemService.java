package com.capstone.service;

import java.util.List;

import com.capstone.entity.Item;

//This interface defines methods for managing items in the inventory system. 
public interface ItemService {

	public int addItem(Item item);
	public List<Item> getInventory();
	public List<Item> getItemsByCategory(String category);
	public int deleteItem(String name, int quantity);
	public Item findById(int id);
	public Item findByName(String name);
}
