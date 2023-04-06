package com.capstone.service;

import java.util.List;

import com.capstone.entity.Purchase;

//This is interface defines two methods: addPurchase and getSalesReport
public interface PurchaseService {

	public int addPurchase(int id, int quantity);
	public List<Purchase> getSalesReport();
}
