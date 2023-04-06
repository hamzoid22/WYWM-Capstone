package com.capstone.service;

import java.util.Date;
import java.util.List;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.entity.Item;
import com.capstone.entity.Purchase;
import com.capstone.repository.ItemRepository;
import com.capstone.repository.PurchaseRepository;

@Service
public class PurchaseSeviceImpl implements PurchaseService {
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	//Adds a purchase to the system and updates the item's quantity and sales records if available.
	@Override
	public int addPurchase(int id, int quantity) {	
		Item item = itemRepository.findById(id).orElse(null);
		
		if (item != null && item.getQuantity() > -(quantity)) {
			if (item.getQuantity() >= quantity) {
				itemRepository.updateQuantity(id, -quantity);
				itemRepository.updateQuantitySold(id, quantity);
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			    Date date = new Date();  
			    String purchaseDate = formatter.format(date);
			    
			    DecimalFormat df = new DecimalFormat("#.##");
			    double profit = ((item.getPrice() - item.getCostPrice()) * quantity);
			    double roundedProfit = Double.parseDouble(df.format(profit));
				
				Purchase purchase = new Purchase();
				purchase.setItem(item);
				purchase.setPurchaseDate(purchaseDate);
				purchase.setQuantity(quantity);
				purchase.setPrice(item.getPrice() * quantity);
				purchase.setProfit(roundedProfit);
				purchaseRepository.save(purchase);
				return 2;
			} else {
				return 1;
			}
			
		} else {
			return 0;
		}
	}

	//Retrieves a list of all purchases in the system.
	@Override
	public List<Purchase> getSalesReport() {
		return purchaseRepository.findAll();
	}

}
