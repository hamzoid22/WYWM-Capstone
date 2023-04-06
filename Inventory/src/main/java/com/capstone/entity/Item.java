package com.capstone.entity;

import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")

//This class represents an item in a system
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Price", nullable = false)
	private double price;

	@Column(name = "Stock Quantity")
	private int quantity;

	@Column(name = "Name", nullable = false)
	private String name;

	@Column(name = "Category", nullable = false)
	private String category;

	@Column(name = "Cost Price", nullable = false)
	private double costPrice;

	@Column(name = "Quantity Sold")
	private int quantitySold;

	@OneToMany(mappedBy = "item")
	@Cascade(CascadeType.DELETE)
	private List<Purchase> purchases;

}
